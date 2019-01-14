package cn.nobitastudio.common.criteria;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Attribute;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * JPA 简单查询条件工具类，在 Bean field 上标注 {@link Criteria} 相关注解自动构建 {@link Specification} 查询条件
 */
public interface SpecificationBuilder {

	final String CRITERIA_INFO = SpecificationBuilder.class.getName() + ".criteriaInfo";
	
	final Map<String, List<PropertyDescriptor>> pdCache = new HashMap<>();

	public default <T> Specification<T> toSpecification() {
		return toSpecification(this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static <T> Specification<T> toSpecification(Object queryObject) {
		Class<?> type = queryObject.getClass();
		List<PropertyDescriptor> chain = pdCache.computeIfAbsent(type.getName(), k -> initPropertyDescriptorChain(type));

		Specification<T> spec = (root, query, cb) -> {
			List<Predicate> result = new LinkedList<>();
			for (PropertyDescriptor pd : chain) {
				PredicateBuilderDelegate pbd  = (PredicateBuilderDelegate) pd.getValue(CRITERIA_INFO);
					
				Predicate predicate = pbd.build(root, query, cb, key -> {
					try {
						Object value = BeanUtils.getPropertyDescriptor(type, key).getReadMethod().invoke(queryObject);
						if (value == null) {
							return null;
						} else {
							Attribute attribute = root.getModel().getAttribute(pbd.getAttributeName());
							return typeSafeGet(key, value, attribute);
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new CriteriaException("创建查询条件时失败: " + e.getMessage(), e);
					}
				});
				
				if (predicate != null) {
					result.add(predicate);
				}
			}
			return result.isEmpty() ? null : cb.and(result.toArray(new Predicate[result.size()]));
		};
		return spec;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static List<PropertyDescriptor> initPropertyDescriptorChain(Class<?> type) {

		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(type);

		List<PropertyDescriptor> result = new LinkedList<>();

		for (PropertyDescriptor pd : pds) {
			// 首先获取方法上的
			Annotation[] annotations = pd.getReadMethod().getAnnotations();
			if (annotations == null) {
				continue;
			}
			Annotation annotation = getCriteriaAnnotation(annotations);
			if (annotation == null) {
				try {
					Field field = type.getDeclaredField(pd.getName());
					annotation = getCriteriaAnnotation(field.getAnnotations());
				} catch (NoSuchFieldException | SecurityException e) {
					// ignore
					continue;
				}
			}

			if (annotation != null) {
				try {
					PredicateBuilderDelegate delegate = new PredicateBuilderDelegate();
					
					Method valueMethod = annotation.getClass().getMethod(Criteria.value);
					String name = (String) valueMethod.invoke(annotation);
					delegate.setAttributeName(name.isEmpty() ? pd.getName() : name);

					Method builderMethod = annotation.getClass().getMethod(Criteria.builder);
					Class<?> builderType = (Class<?>) builderMethod.invoke(annotation);
					PredicateBuilder builder = (PredicateBuilder) builderType.newInstance();
					delegate.setArgName(pd.getName());
					
					delegate.setBuilder(builder);
					delegate.setAnnotation(annotation);
					
					pd.setValue(CRITERIA_INFO, delegate);
					result.add(pd);
				} catch (Exception e) {
					throw new CriteriaException("创建 PredicateBuilder 时出错", e);
				}
			}
		}
		return result.isEmpty() ? Collections.emptyList() : result;
	}

	static Annotation getCriteriaAnnotation(Annotation[] annotations) {
		for (Annotation an : annotations) {
			Criteria criteria = an.annotationType().getAnnotation(Criteria.class);
			if (criteria != null) {
				return an;
			}
		}
		return null;
	}
	
	static Object typeSafeGet(String attributeName, Object value, Attribute<?, ?> attribute) {
		Class<?> argType = value.getClass();
		Class<?> javaType = attribute.getJavaType();
		if (argType.isAssignableFrom(javaType)) {
			return value;
		} else {
			if (argType.isAssignableFrom(LocalDate.class) && javaType.isAssignableFrom(LocalDateTime.class)) {
				LocalDate _0 = (LocalDate) value;
				return LocalDateTime.of(_0, LocalTime.MIN);
			}
		}
		throw new CriteriaException(String.format("无法映射 %s -> %s", argType,  javaType));
	}
}
