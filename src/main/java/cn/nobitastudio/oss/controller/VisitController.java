package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.criteria.CriteriaException;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Visit;
import cn.nobitastudio.oss.service.inter.VisitService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/06 22:01
 * @description
 */
@RestController
@RequestMapping("/visit")
public class VisitController {

    @Inject
    private VisitService visitService;

    @ApiOperation("查询指定号源信息")
    @GetMapping("/{id}")
    public ServiceResult<Visit> getById(@PathVariable("id") Integer id) {
        return ServiceResult.success(visitService.getById(id));
    }

    @ApiOperation("查询分页后的号源信息")
    @PutMapping("/query")
    public ServiceResult<PageImpl<Visit>> query(@RequestBody Visit visit, Pager pager) {
        return ServiceResult.success(visitService.getAll(visit, pager));
    }

    @ApiOperation("删除指定号源信息")
    @DeleteMapping("/{id}")
    public ServiceResult<String> deleteById(@PathVariable("id") Integer id) {
        return ServiceResult.success(visitService.delete(id));
    }

    @ApiOperation("保存或更新号源信息")
    @PostMapping
    public ServiceResult<Visit> save(@RequestBody Visit visit) {
        return ServiceResult.success(visitService.save(visit));
    }

    @ApiOperation("增加指定号源资源数量")
    @GetMapping("/{id}/{count}/plus")
    public ServiceResult<Visit> plus(@PathVariable(name = "id") Integer id, @PathVariable(name = "count") Integer count) {
        return ServiceResult.success(visitService.plus(id, count));
    }

    @ApiOperation("用户进行挂号操作-减少指定号源资源")
    @GetMapping("/{id}/{count}/minus")
    public ServiceResult<Visit> minus(@PathVariable(name = "id") Integer id, @PathVariable(name = "count") Integer count) {
        return ServiceResult.success(visitService.minus(id, count));
    }

    @ApiModelProperty("查询指定医生下的号源信息")
    @GetMapping("/get-by-doctor/{doctorId}")
    public ServiceResult<List<Visit>> getVisitByDoctorId(@PathVariable(name = "doctorId") Integer doctorId) {
        return ServiceResult.success(visitService.getByDoctorId(doctorId));
    }


}
