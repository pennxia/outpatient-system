package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.criteria.CriteriaException;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Department;
import cn.nobitastudio.oss.model.error.ErrorCode;
import cn.nobitastudio.oss.service.inter.DepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:09
 * @description
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Inject
    private DepartmentService departmentService;

    @ApiOperation("查询指定科室信息")
    @GetMapping("/{id}")
    public ServiceResult<Department> getById(@PathVariable("id") Integer id) {
        try {
            return ServiceResult.success(departmentService.getById(id));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("查询科室信息，分页查询.默认不调用该接口")
    @PostMapping("/query")
    public ServiceResult<PageImpl<Department>> query(@RequestBody Department department, Pager pager) {
        try {
            return ServiceResult.success(departmentService.getAll(department, pager));
        } catch (CriteriaException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("查询全部科室信息")
    @GetMapping
    public ServiceResult<List<Department>> query() {
        return ServiceResult.success(departmentService.getAll());
    }

    @ApiOperation("删除指定科室")
    @DeleteMapping("/{id}")
    public ServiceResult<String> deleteById(@PathVariable("id") Integer id) {
        try {
            return ServiceResult.success(departmentService.delete(id));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("保存或更新科室信息")
    @PostMapping
    public ServiceResult<Department> save(@RequestBody Department department) {
        return ServiceResult.success(departmentService.save(department));
    }
}
