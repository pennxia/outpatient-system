package cn.nobitastudio.common.criteria;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义查询
 */
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
@Criteria
public @interface Predicate {

	String value() default "";

	Class<? extends PredicateBuilder<?>> builder();

}