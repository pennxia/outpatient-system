package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.criteria.CriteriaException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Bind;
import cn.nobitastudio.oss.service.inter.BindService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 15:27
 * @description
 */
@RestController
@RequestMapping("/bind")
public class BindController {

    @Inject
    private BindService bindService;

    @ApiOperation("查询指定绑定关系")
    @GetMapping("/{id}")
    public ServiceResult<Bind> getById(@PathVariable("id") Integer id) {
        try {
            return ServiceResult.success(bindService.getById(id));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("查询分页后的绑定关系")
    @PutMapping("/query")
    public ServiceResult<PageImpl<Bind>> query(@RequestBody Bind bind, Pager pager) {
        try {
            return ServiceResult.success(bindService.getAll(bind, pager));
        } catch (CriteriaException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("删除指定绑定")
    @DeleteMapping("/{id}")
    public ServiceResult<String> deleteById(@PathVariable("id") Integer id) {
        try {
            return ServiceResult.success(bindService.delete(id));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("保存或更新绑定信息")
    @PostMapping
    public ServiceResult<Bind> save(@RequestBody Bind bind) {
        return ServiceResult.success(bindService.save(bind));
    }

    @ApiOperation("用户绑定诊疗卡")
    @PutMapping("/bind")
    public ServiceResult<Bind> bind(@RequestBody Bind bind) {
        try {
            return ServiceResult.success(bindService.bind(bind));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiModelProperty("用户解绑诊疗卡")
    @PutMapping("/unbind")
    public ServiceResult<String> unbind(@RequestBody Bind bind) {
        try {
            return ServiceResult.success(bindService.unbind(bind));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }


}
