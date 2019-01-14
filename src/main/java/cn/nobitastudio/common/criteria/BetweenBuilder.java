package cn.nobitastudio.common.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BetweenBuilder implements PredicateBuilder<Between> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> Predicate build(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, CriteriaInfo<Between> context, ArgValueSupplier supplier) {
		Between between = context.getAnnotation();
		Comparable from = supplier.getAndCast(context.getArgName());
		String toKey = between.to();
		Comparable to = null;
		if (toKey != null) {
			to = supplier.getAndCast(toKey);
		}
		if (to != null) {
			return cb.between(root.get(context.getAttributeName()), from, to);
		} else {
			return cb.greaterThanOrEqualTo(root.get(context.getAttributeName()), from);
		}
	}

}
