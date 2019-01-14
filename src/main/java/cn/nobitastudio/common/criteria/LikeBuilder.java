package cn.nobitastudio.common.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class LikeBuilder implements PredicateBuilder<Like>{
	

	@Override
	public <T> Predicate build(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, CriteriaInfo<Like> context, ArgValueSupplier valueSupplier) {
		Object value = valueSupplier.getValue(context.getArgName());
		return cb.like(root.get(context.getAttributeName()), "%" + value.toString() + "%");
	}
	
}
