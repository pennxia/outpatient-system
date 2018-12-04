package cn.nobitastudio.common.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EqualBuilder implements PredicateBuilder<Equal> {

	@Override
	public <T> Predicate build(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, CriteriaInfo<Equal> criteriaInfo, ArgValueSupplier valueSupplier) {
		Object criteriaValue = valueSupplier.getValue(criteriaInfo.getArgName());
		return cb.equal(root.get(criteriaInfo.getAttributeName()), criteriaValue);
	}
}
