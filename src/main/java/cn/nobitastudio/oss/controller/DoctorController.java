package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.criteria.CriteriaException;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Department;
import cn.nobitastudio.oss.entity.Doctor;
import cn.nobitastudio.oss.model.dto.DoctorAndVisit;
import cn.nobitastudio.oss.model.error.ErrorCode;
import cn.nobitastudio.oss.service.inter.DoctorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 13:54
 * @description
 */
@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Inject
    private DoctorService doctorService;

    @ApiOperation("查询指定医生信息")
    @GetMapping("/{id}")
    public ServiceResult<Doctor> getById(@PathVariable("id") Integer id) {
        return ServiceResult.success(doctorService.getById(id));
    }

    @ApiOperation("查询分页后的医生信息")
    @PutMapping("/query")
    public ServiceResult<PageImpl<Doctor>> query(@RequestBody Doctor doctor, Pager pager) {
        return ServiceResult.success(doctorService.getAll(doctor, pager));
    }

    @ApiOperation("删除指定医生基础信息")
    @DeleteMapping("/{id}")
    public ServiceResult<String> deleteById(@PathVariable("id") Integer id) {
        return ServiceResult.success(doctorService.delete(id));
    }

    @ApiOperation("保存或更新医生信息")
    @PostMapping
    public ServiceResult<Doctor> save(@RequestBody Doctor doctor) {
        return ServiceResult.success(doctorService.save(doctor));
    }

    @ApiOperation("查询我的收藏医生")
    @GetMapping("/{userId}/collect-doctor")
    public ServiceResult<List<Doctor>> getCollectDoctor(@PathVariable(name = "userId") Integer userId) {
        return ServiceResult.success(doctorService.getCollectDoctor(userId));
    }

    @ApiOperation("根据科室id 获取指定科室下的全部医生以及其对应最近一周的挂号信息")
    @GetMapping("/byDepartment/{departmentId}")
    public ServiceResult<List<DoctorAndVisit>> getDoctorByDepartmentId(@PathVariable(name = "departmentId") Integer departmentId) {
        return ServiceResult.success(doctorService.getDoctorsByDepartmentId(departmentId));
    }


}
