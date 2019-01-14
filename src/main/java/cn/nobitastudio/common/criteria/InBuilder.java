package cn.nobitastudio.common.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

public class InBuilder implements PredicateBuilder<In> {

	@Override
	public <T> Predicate build(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, CriteriaInfo<In> context, ArgValueSupplier valueSupplier) {
		
		String attributeName = context.getAttributeName();
		
		Object value = valueSupplier.getValue(attributeName);

		if (value instanceof Collection) {
			Collection<?> values = (Collection<?>) value;
			return root.get(attributeName).in(values);
		} else if (value.getClass().isArray()) {
			Object[] values = (Object[]) value;
			return root.get(attributeName).in(values);
		} else {
			return root.get(attributeName).in(value);
		}
	}
}
