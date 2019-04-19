package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.criteria.CriteriaException;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.RegistrationRecord;
import cn.nobitastudio.oss.model.dto.ConfirmRegisterDTO;
import cn.nobitastudio.oss.model.dto.RegisterDTO;
import cn.nobitastudio.oss.model.error.ErrorCode;
import cn.nobitastudio.oss.model.dto.ConfirmOrCancelRegisterDTO;
import cn.nobitastudio.oss.model.vo.RegistrationBasicInfoCollection;
import cn.nobitastudio.oss.model.vo.RegistrationRecordAndOrder;
import cn.nobitastudio.oss.service.inter.RegistrationRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/06 21:59
 * @description
 */
@RestController
@RequestMapping("/registration-record")
public class RegistrationRecordController {

    @Inject
    private RegistrationRecordService registrationRecordService;


    @ApiOperation("查询指定挂号记录")
    @GetMapping("/{id}")
    public ServiceResult<RegistrationRecord> getById(@PathVariable("id") String id) {
        try {
            return ServiceResult.success(registrationRecordService.getById(id));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("查询分页后的挂号记录")
    @PutMapping("/query")
    public ServiceResult<PageImpl<RegistrationRecord>> query(@RequestBody RegistrationRecord registrationRecord, Pager pager) {
        try {
            return ServiceResult.success(registrationRecordService.getAll(registrationRecord, pager));
        } catch (CriteriaException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("删除指定挂号记录")
    @DeleteMapping("/{id}")
    public ServiceResult<String> deleteById(@PathVariable("id") String id) {
        try {
            return ServiceResult.success(registrationRecordService.delete(id));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("保存或更新挂号记录")
    @PostMapping
    public ServiceResult<RegistrationRecord> save(@RequestBody RegistrationRecord registrationRecord) {
        return ServiceResult.success(registrationRecordService.save(registrationRecord));
    }

    @ApiOperation("用户进行挂号操作,进入等待支付状态")
    @PostMapping("/register")
    public ServiceResult<RegistrationRecord> register(@RequestBody RegisterDTO registerDTO) {
        try {
            return ServiceResult.success(registrationRecordService.register(registerDTO));
        } catch (AppException e) {
            return ServiceResult.failure(e);
        } catch (Exception e) {
            return ServiceResult.failure(ErrorCode.get(ErrorCode.UNKNOWN_ERROR),ErrorCode.UNKNOWN_ERROR);
        }
    }

    @ApiOperation("用户支付完成.用户不调用")
    @PutMapping("/confirm")
    public ServiceResult<ConfirmOrCancelRegisterDTO> confirmRegister(@RequestBody ConfirmRegisterDTO confirmRegisterDTO) {
        try {
            return ServiceResult.success(registrationRecordService.confirmRegister(confirmRegisterDTO));
        } catch (AppException e) {
            return ServiceResult.failure(e);
        } catch (Exception e) {
            return ServiceResult.failure("未知异常:" + e.getMessage());
        }
    }

    @ApiOperation("用户取消预约挂号")
    @GetMapping("/{id}/cancel")
    public ServiceResult<ConfirmOrCancelRegisterDTO> cancelRegister(@PathVariable(name = "id") String id) {
        try {
            return ServiceResult.success(registrationRecordService.cancelRegister(id));
        } catch (AppException e) {
            return ServiceResult.failure(e);
        }
    }

    @ApiOperation("得到挂号单以及对应的订单")
    @GetMapping("{userId}/rgt-and-ord")
    public ServiceResult<List<RegistrationRecordAndOrder>> getRegistrationAndOrder(@PathVariable(name = "userId") Integer userId) {
        try {
            return ServiceResult.success(registrationRecordService.getRegistrationAndOrder(userId));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("查询指定挂号的基础信息集合详情")
    @GetMapping("/{registrationId}/basic-info-collection")
    public ServiceResult<RegistrationBasicInfoCollection> getRegistrationBasicInfoCollection(@PathVariable(name = "registrationId") String registrationRecordId) {
        try {
            return ServiceResult.success(registrationRecordService.getRegistrationBasicInfoCollection(registrationRecordId));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

}
