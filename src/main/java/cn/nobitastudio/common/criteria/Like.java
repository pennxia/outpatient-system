package cn.nobitastudio.common.criteria;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 字段模糊查询
 */
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
@Criteria
public @interface Like {

	String value() default "";
	
	String[] args() default "";

	Class<? extends PredicateBuilder<Like>> builder() default LikeBuilder.class;

}
