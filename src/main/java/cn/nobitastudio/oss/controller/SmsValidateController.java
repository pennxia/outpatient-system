package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.oss.model.dto.ConfirmValidateCodeDTO;
import cn.nobitastudio.oss.model.dto.RequestValidateCodeDTO;
import cn.nobitastudio.oss.model.vo.StandardMessage;
import cn.nobitastudio.oss.service.inter.ValidateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/14 13:55
 * @description
 */
@RestController
@RequestMapping("/sms-validate")
public class SmsValidateController {

    @Inject
    private ValidateService validateService;

    @ApiOperation("用户请求短息验证码")
    @PutMapping("/requst-code")
    public ServiceResult<StandardMessage> requestValidateCode(@RequestBody RequestValidateCodeDTO requestValidateCodeDTO) {
        try {
            return ServiceResult.success(validateService.requestValidateCode(requestValidateCodeDTO));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }

    }

    @ApiOperation("用户验证验证码")
    @PutMapping("/confirm-code")
    public ServiceResult<StandardMessage> confirmValidateCode(@RequestBody ConfirmValidateCodeDTO confirmValidateCodeDTO) {
        try {
            return ServiceResult.success(validateService.confirmValidateCode(confirmValidateCodeDTO));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }



}
