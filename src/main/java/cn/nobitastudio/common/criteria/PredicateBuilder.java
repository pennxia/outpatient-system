package cn.nobitastudio.common.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.annotation.Annotation;

/**
 * JPA Predicate 创建器
 */
public interface PredicateBuilder<A extends Annotation> {

	/**
	 * 创建 criteria
	 * 
	 * @param root    spec-root
	 * @param query   spec-query
	 * @param cb      spec-cb
	 * @param context 上下文信息
	 * @return predicate
	 */
	public <T> Predicate build(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, CriteriaInfo<A> context, ArgValueSupplier argValueSupplier);
}
