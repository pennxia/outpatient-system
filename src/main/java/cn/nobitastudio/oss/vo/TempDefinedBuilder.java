package cn.nobitastudio.oss.vo;

import cn.nobitastudio.common.criteria.ArgValueSupplier;
import cn.nobitastudio.common.criteria.CriteriaInfo;
import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.PredicateBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/10 17:34
 * @description
 */
public class TempDefinedBuilder implements PredicateBuilder<Equal> {

    @Override
    public <T> Predicate build(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, CriteriaInfo<Equal> criteriaInfo, ArgValueSupplier valueSupplier) {
        Object criteriaValue = valueSupplier.getValue(criteriaInfo.getArgName());
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("username"),criteriaValue));
        predicates.add(cb.like(root.get("password"),"%" + criteriaValue + "%"));
        return cb.or(predicates.toArray(new Predicate[predicates.size()]));
    }
}
