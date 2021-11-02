package com.TandK.core.cache;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.*;
import com.TandK.core.exception.BizCodeEnum;
import com.TandK.core.exception.RRException;
import com.TandK.util.CommonUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021-02-26
 */
@Slf4j
@Aspect
@Order(1)
@Component
public class RedisCacheAspect {

    /**
     * RedissonClient
     */
    private final RedissonClient redissonClient;

    /**
     * RedisTemplate
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * hutool string format placeholder: {}
     */
    private final static String HUTOOL_FORMAT_PLACE_HOLDER = "{}";

    /**
     * 自旋尝试的最长时间 单位: 毫秒 SECOND default: 300 秒
     * @Value注解记得要根据实际情况在application改
     */
    @Setter
    @Getter
    @Value("${dankal.distribute.cache.spin-sleep-time-in-second:300}")
    private Integer spinTryTimeInSecond;

    /**
     * 1000
     */
    private final static long THOUSANDS_OF_ONE = 1000;

    /**
     * 防止缓存雪崩,缓存有效期需要加上的时间随机数基数 时间单位为 SECOND
     *
     * <pre>
     *      {@code
     *         ...
     *         // 缓存过期时间
     *         long cacheTimeout=100;
     *         // 缓存雪崩随机基数
     *         Integer avalancheCardinal=6;
     *         // 缓存 key
     *         cacheKey=dankal_cache_example_1489843895849
     *         // 缓存内容
     *         cacheContent={... ...}
     *         ...
     *         缓存有效期将会设置为:
     *         cacheTimeout=RandomUtil.randomInt(avalancheCardinal)+cacheTimeout;
     *
     *         redisTemplate.opsForValue().set(cacheKey,cacheContent,cacheTimeout,TimeUnit.MINUTES)
     *      }
     * </pre>
     */
    @Setter
    @Getter
    @Value("${dankal.distribute.cache.avalanche-random-cardinal:30}")
    private Integer avalancheRandomCardinal;


    public RedisCacheAspect(RedissonClient redissonClient, StringRedisTemplate stringRedisTemplate) {
        this.redissonClient = redissonClient;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Around("@annotation(RedisCache)")
    public Object cacheAroundAdvice(ProceedingJoinPoint point) {
        // 环绕通知目标方法 返回值
        Object result;
        // 获取代理方法的签名信息
        MethodSignature signature = (MethodSignature) point.getSignature();
        RedisCache redisCache = signature.getMethod().getAnnotation(RedisCache.class);
        ObjectCacheKey objectCacheKey = signature.getMethod().getAnnotation(ObjectCacheKey.class);
        boolean needCache = this.verifyConditionCache(point);
        try {
            if (null == redisCache || !needCache) {
                if (log.isErrorEnabled() && null == redisCache) {
                    log.error("进入缓存环绕通知,但是 RedisCache Object is null");
                }
                return point.proceed(point.getArgs());
            }
            // 确定 cacheKey
            String cacheKey = getCacheKey(point, redisCache, objectCacheKey);
            if (log.isDebugEnabled()) {
                //log.debug("{} 调用方法 {}  cacheKey {}", Thread.currentThread().getName(), signature.getName(), cacheKey);
            }
            // 尝试缓存命中
            result = cacheHit(signature, cacheKey, true);
            if (result != null) {
                if (log.isDebugEnabled()) {
                    if (log.isDebugEnabled()) {
                        //log.debug("{} 调用方法 {} 命中缓存数据,即将返回响应数据 : {}", Thread.currentThread().getName(), signature.getName(), gson.toJson(result));
                    }
                }
                return result;
            }
            // 缓存未命中
            // 是否使用分布式锁
            boolean useDistributeLock = redisCache.useDistributeLock();
            if (useDistributeLock) {
                String distributedLockKey = getDistributedLockKey(point, redisCache);
                if (log.isDebugEnabled()) {
                    //log.debug("{} 分布式锁 key {}", Thread.currentThread().getName(), distributedLockKey);
                }
                // 使用 Redisson 分布式锁 防止缓存击穿
                RLock lock = redissonClient.getLock(distributedLockKey);
                try {
                    long waitTime = redisCache.redissonTryLockWaitTime();
                    long leaseTime = redisCache.redissonLockLeaseTime();
                    boolean canLock = lock.tryLock(waitTime, leaseTime, redisCache.redissonLockTimeunit());
                    // 判断是否能够获取锁
                    if (canLock) {
                        if (log.isDebugEnabled()) {
                            /*log.debug("{} 即将持有锁: {} {}", Thread.currentThread().getName(), leaseTime,
                                    redisCache.redissonLockTimeunit().name());*/
                        }
                        // 双重检索: 防止重复缓存
                        Object cacheHit = cacheHit(signature, cacheKey, true);
                        if (null != cacheHit) {
                            if (log.isDebugEnabled()) {
                                //log.debug("{} 二次检索命中缓存", Thread.currentThread().getName());
                            }
                            return cacheHit;
                        }
                        // 执行缓存
                        return doRedisCache(point, signature, redisCache, cacheKey);
                    } else {
                        if (log.isDebugEnabled()) {
                            //log.debug("{} 获取分布式锁失败,即将尝试自旋获取缓存数据", Thread.currentThread().getName());
                        }
                        // 获取锁失败
                        return cacheHit(signature, cacheKey, false);
                    }
                } finally {
                    // while: 防止线程A 释放 线程B 的锁
                    while (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                        if (log.isDebugEnabled()) {
                            //log.debug("{} 成功释放分布式锁", Thread.currentThread().getName());
                        }
                    }
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.warn("{} 不使用分布式锁", Thread.currentThread().getName());
                }
                // 不使用分布式锁
                return doRedisCache(point, signature, redisCache, cacheKey);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            if (log.isDebugEnabled()) {
                //log.debug("error  method: {}", signature.getName());
            }
            if (e instanceof RRException) {
                throw (RRException) e;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证条件缓存
     *
     * @param point ProceedingJoinPoint
     * @return 如果需要缓存则返回 true 否则返回 false
     */
    private boolean verifyConditionCache(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        CacheCondition cacheCondition = signature.getMethod().getAnnotation(CacheCondition.class);
        if (null == cacheCondition) {
            // 直接缓存 不存在条件混存
            return true;
        }
        Class<?> objectConditionClass = cacheCondition.objectConditionClass();
        String[] conditionsFieldNames = cacheCondition.conditionsFieldNames();
        if (Object.class.getName().equals(objectConditionClass.getName())) {
            //  object 是默认值
            if (ArrayUtil.isNotEmpty(conditionsFieldNames)) {
                // 当 objectConditionClass 取值 为 object 时,conditionsFieldNames 应该为空才合理
                String errorMessage = StrUtil.format(
                        "verifyConditionCache  ==> objectConditionClass is {}. but the conditionsFieldNames is not null [{}]",
                        objectConditionClass.getName(),
                        conditionsFieldNames
                );
                log.error("verifyConditionCache  ==> {}", errorMessage);
                throw new IllegalStateException(errorMessage);
            }
            // 基本数据类型条件验证
            return this.verifyPrimitiveTypeCondition(point, cacheCondition);
        } else {
            // 对象条件类型验证
            return this.verifyObjectCondition(point, cacheCondition);
        }
    }

    private boolean verifyObjectCondition(ProceedingJoinPoint point, CacheCondition cacheCondition) {
        Class<?> conditionClass = cacheCondition.objectConditionClass();
        int index = cacheCondition.objectConditionParameterIndex();
        if (log.isDebugEnabled()) {
            //log.debug("verifyObjectCondition  ==> index: {}", index);
        }
        Object conditionObject = point.getArgs()[index];
        String[] conditionsFieldNames = cacheCondition.conditionsFieldNames();
        if (log.isDebugEnabled()) {
            //log.debug("verifyObjectCondition  ==> conditionsFieldNames: {}", ArrayUtil.toString(conditionsFieldNames));
        }
        String[] conditionsValues = cacheCondition.conditionsValues();
        if (log.isDebugEnabled()) {
            //log.debug("verifyObjectCondition  ==> conditionsValues: {}", ArrayUtil.toString(conditionsValues));
        }
        for (int i = 0; i < conditionsFieldNames.length; i++) {
            Field field = ReflectUtil.getField(conditionClass, conditionsFieldNames[i]);
            Object fieldValue = ReflectUtil.getFieldValue(conditionObject, field);
            if (!(fieldValue instanceof String)) {
                fieldValue = String.valueOf(fieldValue);
            }
            if (ObjectUtil.notEqual(fieldValue, conditionsValues[i])) {
                if (log.isDebugEnabled()) {
                    //log.debug("verifyObjectCondition ==> {} not equals {}", fieldValue, conditionsValues[i]);
                    //log.debug("verifyObjectCondition  ==> false");
                }
                return false;
            }
        }
        if (log.isDebugEnabled()) {
            //log.debug("verifyObjectCondition  ==> true");
        }
        return true;
    }

    private boolean verifyPrimitiveTypeCondition(ProceedingJoinPoint point, CacheCondition cacheCondition) {
        String[] conditionsValues = cacheCondition.conditionsValues();
        Object[] args = point.getArgs();
        if (log.isDebugEnabled()) {
            //log.debug("primitiveTypeCondition  ==>  conditionsValues:{} args:{}", ArrayUtil.toString(conditionsValues), ArrayUtil.toString(args));
        }
        for (int i = 0; i < conditionsValues.length; i++) {
            if (ObjectUtil.notEqual(conditionsValues[i], args[i])) {
                if (log.isDebugEnabled()) {
                    //log.debug("primitiveTypeCondition ==> {} not equals {}", args[i], conditionsValues[i]);
                    //log.debug("primitiveTypeCondition  ==> false");
                }
                return false;
            }
        }
        if (log.isDebugEnabled()) {
            //log.debug("primitiveTypeCondition  ==> true");
        }
        return true;
    }


    /**
     * 获取 分布式锁 key
     *
     * @param point      ProceedingJoinPoint
     * @param redisCache RedisCache
     * @return 分布式锁 key
     */
    private String getDistributedLockKey(ProceedingJoinPoint point, RedisCache redisCache) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        ObjectDistributeLockKey objectDistributeLockKey = signature.getMethod().getAnnotation(ObjectDistributeLockKey.class);
        if (null != objectDistributeLockKey) {
            // object distribute lock key 优先级高于  redisCache
            return this.getDynamicObjectDistributeLockKey(point, objectDistributeLockKey);
        }
        String distributedLockKey;
        // 获取 分布式锁 的 key
        if (redisCache.dynamicDistributedLockKey()) {
            // 动态分布式锁的 key
            String dynamicDistributedLockKeyPattern = redisCache.dynamicDistributedLockKeyPattern();
            if (StrUtil.isBlank(dynamicDistributedLockKeyPattern)) {
                throw new IllegalArgumentException(
                        "使用 动态的分布式锁key(dynamicDistributedLockKey=ture),但是未指定 dynamicDistributedLockKeyPattern");
            }
            if (!dynamicDistributedLockKeyPattern.contains(HUTOOL_FORMAT_PLACE_HOLDER)) {
                throw new IllegalArgumentException(
                        "dynamicDistributedLockKeyPattern 必须含有{} 占位符.详情请参阅: cn.hutool.core.util.StrUtil.format(java.lang.CharSequence, java.lang.Object...)");
            }
            int[] dynamicDistributedLockKeyParameterIndexArray =
                    redisCache.dynamicDistributedLockKeyParameterIndexArray();
            Object[] patternParameters = new Object[dynamicDistributedLockKeyParameterIndexArray.length];
            for (int i = 0; i < dynamicDistributedLockKeyParameterIndexArray.length; i++) {
                patternParameters[i] = point.getArgs()[dynamicDistributedLockKeyParameterIndexArray[i]];
            }
            distributedLockKey = StrUtil.format(dynamicDistributedLockKeyPattern, patternParameters);
        } else {
            // 直接 指定 分布式锁的 key
            distributedLockKey = redisCache.distributedLockKey();
            if (StrUtil.isBlank(distributedLockKey)) {
                throw new IllegalStateException(
                        "未使用 动态的分布式锁key(dynamicDistributedLockKey=false),但是未指定 distributedLockKey");
            }
        }
        return distributedLockKey;
    }

    private String getDynamicObjectDistributeLockKey(ProceedingJoinPoint point, ObjectDistributeLockKey objectDistributeLockKey) {
        Class<?> objectClass = objectDistributeLockKey.objectClass();
        String[] fieldNames = objectDistributeLockKey.fieldNames();
        Object[] patternValues = new Object[fieldNames.length];
        int index = objectDistributeLockKey.objectParameterIndex();
        Object object = point.getArgs()[index];
        for (int i = 0; i < fieldNames.length; i++) {
            Field field = ReflectUtil.getField(objectClass, fieldNames[i]);
            Object fieldValue = ReflectUtil.getFieldValue(object, field);
            patternValues[i] = fieldValue;
        }
        if (log.isDebugEnabled()) {
          /*  log.debug(
                    "getDynamicObjectDistributeLockKey  ==> dynamicDistributeLockKeyPattern {} patternValues {}",
                    objectDistributeLockKey.dynamicDistributeLockKeyPattern(),
                    ArrayUtil.toString(patternValues)
            );*/
        }
        String dynamicDistributeLockKey = StrUtil.format(objectDistributeLockKey.dynamicDistributeLockKeyPattern(), patternValues);
        if (log.isDebugEnabled()) {
            // log.debug("getDynamicObjectDistributeLockKey  ==> dynamicDistributeLockKey {}", dynamicDistributeLockKey);
        }
        return dynamicDistributeLockKey;
    }

    /**
     * 获取 cache key
     *
     * @param point          ProceedingJoinPoint
     * @param redisCache     RedisCache
     * @param objectCacheKey objectCacheKey
     * @return the cache key
     */
    private String getCacheKey(ProceedingJoinPoint point, RedisCache redisCache, ObjectCacheKey objectCacheKey) {
        if (null != objectCacheKey) {
            // 对象缓存key 优先级高于 redisCache 配置的 cacheKey
            return getObjectCacheKey(point, objectCacheKey);
        }
        String cacheKey;
        boolean dynamicCacheKey = redisCache.dynamicCacheKey();
        if (dynamicCacheKey) {
            if (log.isDebugEnabled()) {
                //log.debug("使用动态缓存key");
            }
            // 动态 缓存key
            String dynamicCacheKeyPattern = redisCache.dynamicCacheKeyPattern();
            if (StrUtil.isBlank(dynamicCacheKeyPattern)) {
                throw new IllegalStateException("使用动态缓存key. 但是尚未配置 动态key pattern(dynamicCacheKeyPattern)");
            }
            if (!dynamicCacheKeyPattern.contains(HUTOOL_FORMAT_PLACE_HOLDER)) {
                throw new IllegalArgumentException(
                        "dynamicCacheKeyPattern 必须含有{} 占位符.详情请参阅: cn.hutool.core.util.StrUtil.format(java.lang.CharSequence, java.lang.Object...)");
            }
            int[] indexArray = redisCache.dynamicCacheKeyParameterIndexArray();
            Object[] patternParameters = new Object[indexArray.length];
            for (int i = 0; i < indexArray.length; i++) {
                patternParameters[i] = point.getArgs()[indexArray[i]];
            }
            cacheKey = StrUtil.format(dynamicCacheKeyPattern, patternParameters);
        } else {
            // 使用指定的 cache key
            cacheKey = redisCache.cacheKey();
            if (StrUtil.isBlank(cacheKey)) {
                throw new IllegalStateException("未使用动态缓存key,但是亦未指定 cacheKey");
            }
        }
        return cacheKey;
    }

    private String getObjectCacheKey(ProceedingJoinPoint point, ObjectCacheKey objectCacheKey) {
        Class<?> objectClass = objectCacheKey.objectClass();
        int index = objectCacheKey.objectParameterIndex();
        if (log.isDebugEnabled()) {
            //log.debug("getObjectCacheKey  ==> index {}", index);
        }
        String[] fieldNames = objectCacheKey.fieldNames();
        if (log.isDebugEnabled()) {
            //log.debug("getObjectCacheKey ==> fieldNames : {}", ArrayUtil.toString(fieldNames));
        }
        Object object = point.getArgs()[index];
        Object[] patternValues = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            Field field = ReflectUtil.getField(objectClass, fieldNames[i]);
            Object fieldValue = ReflectUtil.getFieldValue(object, field);
            patternValues[i] = fieldValue;
        }
        if (log.isDebugEnabled()) {
            //log.debug("getObjectCacheKey ==> dynamicCacheKeyPattern : {} patternValues: {} ", objectCacheKey.dynamicCacheKeyPattern(), ArrayUtil.toString(patternValues));
        }
        return StrUtil.format(objectCacheKey.dynamicCacheKeyPattern(), patternValues);
    }

    /**
     * 将执行结果缓存
     *
     * @param point      ProceedingJoinPoint
     * @param signature  MethodSignature
     * @param redisCache RedisCache
     * @param cacheKey   cache key
     * @return 执行结果数据
     * @throws Throwable 目标方法执行异常 / signature.getReturnType().newInstance() 执行异常(防止缓存穿透)
     */
    private Object doRedisCache(ProceedingJoinPoint point, MethodSignature signature, RedisCache redisCache,
                                String cacheKey) throws Throwable {
        Object result = point.proceed(point.getArgs());
        if (null == result) {
            // 防止 缓存穿透
            Object nullObj = this.createEmptyObject(signature);
            stringRedisTemplate.opsForValue().set(cacheKey, CommonUtil.getJsonString(nullObj),
                    redisCache.emptyObjectCacheTimeout(), TimeUnit.MINUTES);
            return nullObj;
        }
        long cacheTimeout = redisCache.cacheTimeout();
        if (cacheTimeout > 0) {
            // 缓存具有 TTL
            stringRedisTemplate.opsForValue().set(cacheKey, CommonUtil.getJsonString(result), cacheTimeout,
                    redisCache.cacheTimeoutUnit());
            // 防止缓存雪崩
            // TTL 可能的单位: day hours minutes ... ...
            // 为了屏蔽不同时间单位,先添加缓存,然后统一使用 SECOND 单位获取 缓存过期时间,然后再
            // 在原来过期时间的基础上添加随机数,防止缓存雪崩
            Long expire = stringRedisTemplate.getExpire(cacheKey, TimeUnit.SECONDS);
            if (null == expire) {
                throw new IllegalStateException("设置了缓存有效期,但是在做防止缓存雪崩的操作时,`redisTemplate.getExpire`获取过期时间失效");
            }
            if (log.isDebugEnabled()) {
                //log.debug("缓存 {} 初始TTL:{} 秒", cacheKey, expire);
            }
            expire = expire + RandomUtil.randomInt(avalancheRandomCardinal);
            if (log.isDebugEnabled()) {
                //log.debug("缓存 {} 防止缓存雪崩更TTL为:{} 秒", cacheKey, expire);
            }
            stringRedisTemplate.expire(cacheKey, expire, TimeUnit.SECONDS);
        } else {
            // 永久缓存
            stringRedisTemplate.opsForValue().set(cacheKey, CommonUtil.getJsonString(result));
        }
        return result;
    }

    /**
     * 缓存命中
     *
     * @param signature MethodSignature
     * @param key       cache key
     * @param isHit     是否命中缓存
     *                  <p>
     *                  isHit: true 命中缓存 <br/>
     *                  isHit: false 获取 分布式锁失败 使用自旋的方式获取缓存
     *                  </p>
     * @return null / 缓存数据 / 自旋上限异常
     * @throws IllegalStateException 线程自旋尝试上限(读取缓存)
     */
    private Object cacheHit(MethodSignature signature, String key, boolean isHit) throws IllegalStateException {
        // 获取数据 redis 的 String数据类型: key,value 都是JSON 字符串
        String cache = stringRedisTemplate.opsForValue().get(key);
        // 命中缓存
        if (StrUtil.isNotBlank(cache)) {
            // 有数据 ，则将数据进行转化
            return CommonUtil.jsonToObject(cache, TypeUtil.getReturnType(signature.getMethod()));
        }
        if (isHit) {
            // 尝试缓存命中
            if (log.isDebugEnabled()) {
                //log.debug("{} 调用方法 {} 未成功命中缓存 ", Thread.currentThread().getName(), signature.getName());
            }
            return null;
        }
        // 未命中缓存,线程进入自旋尝试获取缓存数据
        final long startMs = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startMs) <= spinTryTimeInSecond * THOUSANDS_OF_ONE) {
            String cacheContent = stringRedisTemplate.opsForValue().get(key);
            // 命中缓存
            if (StrUtil.isNotBlank(cacheContent)) {
                if (log.isDebugEnabled()) {
                    //log.debug("{} 调用方法 {}  现在 成功获取到缓存数据", Thread.currentThread().getName(), signature.getName());
                }
                // 有数据 ，则将数据进行转化
                return CommonUtil.jsonToObject(cacheContent, TypeUtil.getReturnType(signature.getMethod()));
            }
        }
        // 自旋上限仍未获取到模板数据
        throw new RRException("执行缓存获取失败[自旋上限],Timeout: " + spinTryTimeInSecond + " 秒", BizCodeEnum.SYSTEM_ERROR.getCode());
        // throw new SpinTryGetCacheException("执行缓存获取失败[自旋上限]");
    }

    /**
     * 缓存穿透: 空对象创建
     *
     * @param methodSignature methodSignature
     * @return Object
     * @throws Exception
     */
    private Object createEmptyObject(MethodSignature methodSignature) throws Exception {
        String returnTypeName = methodSignature.getReturnType().getName();
        if (List.class.getName().equals(returnTypeName)) {
            return new ArrayList<>(1);
        } else if (Map.class.getName().equals(returnTypeName)) {
            return MapUtil.newHashMap(1);
        }
        return methodSignature.getReturnType().getConstructor().newInstance();
    }
}
