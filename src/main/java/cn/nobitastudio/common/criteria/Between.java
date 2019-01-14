package cn.nobitastudio.common.criteria;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 字段范围查询
 */
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
@Criteria
public @interface Between {

	String value() default "";
	
	/**
	 * 下界参数名称
	 */
	String to() default "";
	
	Class<? extends PredicateBuilder<Between>> builder() default BetweenBuilder.class;

}