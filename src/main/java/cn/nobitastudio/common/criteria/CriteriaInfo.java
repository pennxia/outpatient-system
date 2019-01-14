package cn.nobitastudio.common.criteria;

import java.lang.annotation.Annotation;

/**
 * 解析后的 criteria 信息
 */
public interface CriteriaInfo<A extends Annotation> {

	/**
	 * SQL 查询字段名
	 */
	public String getAttributeName();

	/**
	 * 注解字段
	 * 
	 * @return
	 */
	public String getArgName();

	/**
	 * 获取标注字段的注解对象
	 * @return
	 */
	public A getAnnotation();

}
