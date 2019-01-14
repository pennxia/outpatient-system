package cn.nobitastudio.common.criteria;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 字段相等标注
 */
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
@Criteria
public @interface Equal {

	String value() default "";

	Class<? extends PredicateBuilder<Equal>> builder() default EqualBuilder.class;

}