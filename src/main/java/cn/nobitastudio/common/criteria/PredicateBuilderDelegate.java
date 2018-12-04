package cn.nobitastudio.common.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.annotation.Annotation;

class PredicateBuilderDelegate<A extends Annotation> implements CriteriaInfo<A> {

	private String attributeName;
	private String argName;
	private A annotation;
	private PredicateBuilder<A> builder;
	
	// ~~ parent ~~
	@Override
	public String getAttributeName() {
		return this.attributeName;
	}

	@Override
	public String getArgName() {
		return this.argName;
	}

	@Override
	public A getAnnotation() {
		return this.annotation;
	}

	void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	void setArgName(String fieldName) {
		this.argName = fieldName;
	}

	void setAnnotation(A annotation) {
		this.annotation = annotation;
	}

	void setBuilder(PredicateBuilder<A> builder) {
		this.builder = builder;
	}

	<T> Predicate build(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, ArgValueSupplier valueSupplier) {
		if ( null == valueSupplier.getValue(this.argName)) {
			return null;
		}
		return builder.build(root, query, cb, this, valueSupplier);
	}
}
