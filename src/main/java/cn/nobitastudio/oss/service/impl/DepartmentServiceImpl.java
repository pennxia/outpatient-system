package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Department;
import cn.nobitastudio.oss.repo.DepartmentRepo;
import cn.nobitastudio.oss.service.inter.DepartmentService;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 12:39
 * @description
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static final String DELETE_SUCCESS = "科室信息删除成功";
    private static final String SAVE_OR_UPDATE_SUCCESS = "科室信息添加或修改成功";

    @Inject
    private DepartmentRepo departmentRepo;

    /**
     * 查询指定id科室信息
     *
     * @param id 指定科室id
     * @return
     */
    @Override
    public Department getById(Integer id) {
        return departmentRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定用户"));
    }

    /**
     * 查询所有
     *
     * @param pager 分页参数
     * @return
     */
    @Override
    public PageImpl<Department> getAll(Department department, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(), pager.getLimit(), Sort.by(Sort.Direction.ASC, "id"));
        Page<Department> departments = departmentRepo.findAll(SpecificationBuilder.toSpecification(department), pageable);
        return new PageImpl<>(departments.getContent(), pageable, departments.getTotalElements());
    }

    /**
     * 删除指定id科室
     *
     * @param id 指定科室id
     * @return
     */
    @Override
    public String delete(Integer id) {
        departmentRepo.delete(departmentRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定用户")));
        return DELETE_SUCCESS;
    }

    /**
     * 新增或更新科室信息
     *
     * @param department 待新增或更新的科室信息
     * @return
     */
    @Override
    public Department save(Department department) {
        return departmentRepo.save(department);
    }
}
