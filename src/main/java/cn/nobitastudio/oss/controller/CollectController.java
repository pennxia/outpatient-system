package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.oss.entity.CollectDoctor;
import cn.nobitastudio.oss.service.inter.CollectDoctorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/13 22:20
 * @description
 */
@RestController
@RequestMapping("/collect-doctor")
public class CollectController {

    @Inject
    private CollectDoctorService collectDoctorService;

    @ApiOperation("用户收藏某个医生")
    @PostMapping
    public ServiceResult<CollectDoctor> collect(@RequestBody CollectDoctor collectDoctor) {
        try {
            return ServiceResult.success(collectDoctorService.collect(collectDoctor));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("用户取消收藏")
    @PostMapping("/cancel-collect")
    public ServiceResult<String> cancelCollect(@RequestBody CollectDoctor collectDoctor) {
        try {
            return ServiceResult.success(collectDoctorService.unCollect(collectDoctor));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }


}
