package com.TandK.core.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * 条件缓存
 *
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021-03-19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheCondition {

    /**
     * Object条件 class
     * <pre>
     *     如果条件是基本数据类型,则直接顺序指定 {@link #conditionsValues()} 即可
     * </pre>
     */
    Class<?> objectConditionClass() default Object.class;

    /**
     * 条件对象所在方法参数的索引
     *
     */
    int objectConditionParameterIndex() default 0;

    /**
     * 条件 属性名称 {@link Field#getName()}
     * <pre>
     *      元素顺序需要和 {@link #conditionsValues()} 保持一致
     *      如果条件是基本数据类型,则直接顺序指定 {@link #conditionsValues()} 即可
     * </pre>
     */
    String[] conditionsFieldNames() default {};

    /**
     * 条件属性值 {@link Field#get(Object)}
     * <pre>
     *       当使用 {@link #objectConditionClass()} 时 元素顺序需要和 {@link #conditionsFieldNames()} 保持一致
     *       如果是基本数据类型,则只需要和方法参数保持一致即可.
     * </pre>
     */
    String[] conditionsValues();

}
