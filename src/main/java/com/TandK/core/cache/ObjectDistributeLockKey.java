package com.TandK.core.cache;

import cn.hutool.core.util.StrUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ObjectDistributeLockKey  分布式锁动态key: pattern value 从对象中获取
 * 此注解的对于指定 key 的优先级高于 {@link RedisCache#distributedLockKey()} ()}  {@link RedisCache#dynamicDistributedLockKeyPattern()}
 *
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021/03/21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectDistributeLockKey {
    /**
     * 对象 类型
     */
    Class<?> objectClass();

    /**
     * 对象所在参数索引
     */
    int objectParameterIndex() default 0;

    /**
     * 动态缓存 key pattern
     * <pre>
     *   动态 缓存 key pattern  {@link StrUtil#format(CharSequence, Object...)}
     *   占位符顺序需要和 {@link #fieldNames()} 一致
     * </pre>
     */
    String dynamicDistributeLockKeyPattern();

    /**
     * 参与动态缓存 key 构成的属性名称
     * <pre>
     *     顺序需要和 {@link #dynamicDistributeLockKeyPattern()} 保持一致
     * </pre>
     */
    String[] fieldNames();
}
