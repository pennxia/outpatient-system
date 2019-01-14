package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Role;
import cn.nobitastudio.oss.entity.UserRole;
import cn.nobitastudio.oss.repo.RoleRepo;
import cn.nobitastudio.oss.repo.UserRoleRepo;
import cn.nobitastudio.oss.service.inter.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/09 11:02
 * @description
 */
@Service
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DELETE_SUCCESS = "角色信息删除成功";

    private static final Integer DEFAULT_PAGE_NO = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 10000;

    @Inject
    private RoleRepo roleRepo;
    @Inject
    private UserRoleRepo userRoleRepo;

    /**
     * 查询指定id角色信息
     *
     * @param id 角色id
     * @return
     */
    @Override
    public Role getById(Integer id) {
        return roleRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定用户"));
    }

    /**
     * 查询所有Role,默认已经分页
     *
     * @param pager
     * @return
     */
    @Override
    public PageImpl<Role> getAll(Role role, Pager pager) {
        if (pager == null) {
            pager = new Pager(DEFAULT_PAGE_NO, DEFAULT_PAGE_SIZE);
        }
        Pageable pageable = PageRequest.of(pager.getPage(), pager.getLimit(), Sort.by(Sort.Direction.ASC, "id"));
        Page<Role> roles = roleRepo.findAll(SpecificationBuilder.toSpecification(role), pageable);
        return new PageImpl<>(roles.getContent(), pageable, roles.getTotalElements());
    }

    /**
     * 删除指定Role
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public String deleteById(Integer id) {
        roleRepo.delete(roleRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定id的角色信息")));
        return DELETE_SUCCESS;
    }

    /**
     * 新增或者修改指定Role
     *
     * @param role
     * @return
     */
    @Transactional
    @Override
    public Role save(Role role) {
        return roleRepo.save(role);
    }

    /**
     * 给指定用户给予相应的角色
     *
     * @param userRole
     * @return
     */
    @Transactional
    @Override
    public UserRole bindRole(UserRole userRole) {
        if (userRoleRepo.findByUserRole(userRole).isPresent()) {
            // 已经存在
            throw new AppException("授权失败,该用户已拥有该角色");
        }
        return userRoleRepo.save(userRole);
    }

    /**
     * 给指定用户给予相应的角色
     *
     * @param userRole
     * @return
     */
    @Transactional
    @Override
    public String unbindRole(UserRole userRole) {
        if (!userRoleRepo.findByUserRole(userRole).isPresent()) {
            // 已经存在
            throw new AppException("取消授权失败,该用户尚未拥有该角色");
        }
        userRoleRepo.deleteByUserRole(userRole);
        return DELETE_SUCCESS;
    }
}
