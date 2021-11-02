package com.TandK.core.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * redis 缓存注解
 * <p>
 * 在需要使用  redis 缓存的 方法上 使用 当前注解
 * </p>
 *
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021-02-26
 * @see RedisCacheAspect
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {

    /**
     * 缓存 key
     * <p>
     * dynamicCacheKey=false 时,直接使用 cacheKey  作为缓存可以
     * </p>
     *
     * @return 缓存 key
     */
    String cacheKey() default "";

    /**
     * 是否使用 动态 缓存key
     * <p>
     * etc. product_item_13245253625232
     * </p>
     *
     * @return 是否使用 动态 缓存key
     */
    boolean dynamicCacheKey() default true;

    /**
     * 动态 缓存 key pattern  etc. "product_item_{}"
     * <pre>
     *   dynamicCacheKey=true
     *   具体请参阅
     *   cn.hutool.core.util.StrUtil#format(java.lang.CharSequence, java.lang.Object...)
     * </pre>
     *
     * @return 动态 缓存 key pattern  etc. "product_item_{}"
     */
    String dynamicCacheKeyPattern() default "";

    /**
     * 动态缓存 key 动态值所在方法参数的位置索引
     * <pre>
     * {@code
     *   dynamicCacheKey=true
     *     etc.
     *
     *   dynamicCacheKeyParameterIndexArray = {0}
     *   dynamicCacheKeyPattern=product_item_{}
     *
     *    public Product getProduct(Integer productId,...){
     *       ... ...
     *    }
     *
     *    getProduct(1324536795953)
     *    the full cache key :  product_item_1324536795953
     * }
     * </pre>
     *
     * @return 动态缓存 key 动态值所在方法参数的位置索引
     */
    int[] dynamicCacheKeyParameterIndexArray() default {0};

    /**
     * 缓存 过期时间
     * <p>
     * 如果 cacheTimeout 小于等于0 则表示缓存属于永久缓存
     * <br/>
     * 当且仅当 cacheTimeout 的值 大于 0 时 当前配置才生效
     * </p>
     *
     * @return 缓存 过期时间
     */
    long cacheTimeout() default 0;

    /**
     * 缓存过期时间单位
     *
     * @return 缓存过期时间单位
     */
    TimeUnit cacheTimeoutUnit() default TimeUnit.MINUTES;

    /**
     * 为了避免缓存穿透,我们需要对空值进行缓存
     * 空值缓存 过期时间 (单位: 分钟)
     *
     * @return 空值缓存 过期时间
     */
    long emptyObjectCacheTimeout() default 5;

    /**
     * 是否使用分布式锁
     *
     * @return 是否使用分布式锁
     */
    boolean useDistributeLock() default false;

    /**
     * 分布式锁  key
     * <p>
     * 当且仅当 <code>useDistributeLock=true</code>时,当前配置才会生效(使用)
     * <br/>
     * distributedLockKey 不能为空
     * </p>
     *
     * @return 分布式锁  key
     */
    String distributedLockKey() default "";

    /**
     * 是否使用 动态 distributedLockKey
     * <pre>
     *   distributedLockKey=false
     *   直接使用  distributedLockKey 作为 分布式锁 的 key
     *
     *   distributedLockKey=true
     *   dynamicDistributedLockKeyPattern 和 dynamicDistributedLockKeyParameterIndexArray 动态生成分布式锁 的 key
     * </pre>
     *
     * @return boolean
     */
    boolean dynamicDistributedLockKey() default true;

    /**
     * 动态 分布式锁  key pattern  etc. "product_item_{}"
     * <pre>
     *  dynamicDistributedLockKey=true
     *  cn.hutool.core.util.StrUtil#format(java.lang.CharSequence, java.lang.Object...)
     * </pre>
     *
     * @return 动态 分布式锁  key pattern  etc. "product_item_{}"
     */
    String dynamicDistributedLockKeyPattern() default "";

    /**
     * 动态分布式锁 key 动态值所在方法参数的位置索引 数组
     * <pre>
     * <text>
     * dynamicDistributedLockKey=true
     *   etc.
     *  dynamicDistributedLockKeyParameterIndexArray ={0}
     *  dynamicDistributedLockKeyPattern=product_item_{}
     *           {@code
     *            public Product getProduct(Integer productId,...){
     *             ... ...
     *            }
     *          }
     * getProduct(1324536795953)
     * the full distributed lock key :  product_item_1324536795953
     *   </text>
     * </pre>
     *
     * @return 动态分布式锁 key 动态值所在方法参数的位置索引 数组
     */
    int[] dynamicDistributedLockKeyParameterIndexArray() default {0};

    /**
     * 尝试获取 redisson lock  等待的时间
     * <p>
     * 当且仅当 <code>isUseDistributeLock=true</code>时,当前配置才会生效(使用)
     * <br/>
     * 此值只用于避免 redis 服务器网络延迟,不易设置过大 切切
     * </p>
     *
     * @return 尝试获取 redisson lock  等待的时间
     */
    long redissonTryLockWaitTime() default 120;

    /**
     * 持有 redisson lock 的时间
     * <p>
     * 当且仅当 <code>isUseDistributeLock=true</code>时,当前配置才会生效(使用)
     * </p>
     *
     * @return 持有 redisson lock 的时间
     */
    long redissonLockLeaseTime() default 230;

    /**
     * redisson lock 时间单位
     * <p>
     * <text>
     * redissonTryLockWaitTime
     * redissonLockLeaseTime
     * </text>
     * </p>
     *
     * @return redisson lock 时间单位
     */
    TimeUnit redissonLockTimeunit() default TimeUnit.MILLISECONDS;

}
