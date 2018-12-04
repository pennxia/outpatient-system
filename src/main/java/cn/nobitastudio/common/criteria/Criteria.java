package cn.nobitastudio.common.criteria;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标识一个注解支持 criteria 自动化构建
 */
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface Criteria {

	static String value = "value";
	static String builder = "builder";
}
