package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.criteria.CriteriaException;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Role;
import cn.nobitastudio.oss.entity.UserRole;
import cn.nobitastudio.oss.service.inter.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/09 11:12
 * @description
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Inject
    private RoleService roleService;

    @ApiOperation("查询分页后的所有角色信息")
    @GetMapping("/{id}")
    public ServiceResult<Role> getById(Integer id) {
        return ServiceResult.success(roleService.getById(id));
    }

    @ApiOperation("查询分页后的角色信息")
    @PutMapping("/query")
    public ServiceResult<PageImpl<Role>> query(@RequestBody Role role, Pager pager) {
        return ServiceResult.success(roleService.getAll(role, pager));
    }

    @ApiOperation("删除指定角色信息")
    @DeleteMapping("/{id}")
    public ServiceResult<String> deleteById(@PathVariable("id") Integer id) {
        return ServiceResult.success(roleService.deleteById(id));
    }

    @ApiOperation("保存或更新角色信息")
    @PostMapping
    public ServiceResult<Role> save(@RequestBody Role role) {
        return ServiceResult.success(roleService.save(role));
    }

    @ApiOperation("给用户进行授予相应角色")
    @PostMapping("/bind-role")
    public ServiceResult<UserRole> bindRole(@RequestBody UserRole userRole) {
        return ServiceResult.success(roleService.bindRole(userRole));
    }

    @ApiOperation("给用户进行取消授权相应角色")
    @PostMapping("/unbind-role")
    public ServiceResult<String> unbindRole(@RequestBody UserRole userRole) {
        return ServiceResult.success(roleService.unbindRole(userRole));
    }


}
