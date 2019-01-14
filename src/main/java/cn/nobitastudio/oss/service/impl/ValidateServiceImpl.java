package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.oss.helper.ValidateCodeContainHelper;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.oss.model.dto.ConfirmValidateCodeDTO;
import cn.nobitastudio.oss.model.dto.RequestValidateCodeDTO;
import cn.nobitastudio.oss.model.dto.ValidateCode;
import cn.nobitastudio.oss.model.vo.SmsSendResult;
import cn.nobitastudio.oss.model.vo.StandardMessage;
import cn.nobitastudio.oss.repo.UserRepo;
import cn.nobitastudio.oss.service.inter.ValidateService;
import cn.nobitastudio.oss.helper.SmsHelper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/14 14:06
 * @description
 */
@Service
public class ValidateServiceImpl implements ValidateService {

    @Inject
    private ValidateCodeContainHelper validateCodeContainHelper; // 验证码存储容器
    @Inject
    private SmsHelper smsHelper;
    @Inject
    private UserRepo userRepo;
    @Inject
    private ExecutorService executorService;

    @Override
    public StandardMessage requestValidateCode(RequestValidateCodeDTO requestValidateCodeDTO) {
        StandardMessage standardMessage = new StandardMessage();
        // 生成验证码
        ValidateCode validateCode = new ValidateCode();
        // 进行发送
        SmsSendResult smsSendResult = smsHelper.sendSms(smsHelper.initRequestValidateCodeSms(userRepo.findById(requestValidateCodeDTO.getUserId())
                .orElseThrow(() -> new AppException("未查找到指定用户")).getMobile(), requestValidateCodeDTO.getSmsMessageType(), validateCode));
        if (smsSendResult.getResult().equals(Boolean.TRUE)) {
            // 发送成功
            standardMessage.setFlagCode(StandardMessage.VALIDATION_CODE_SEND_SUCCESS_FLAG);
            standardMessage.setDescription(StandardMessage.VALIDATION_CODE_SEND_SUCCESS_DESC);
            // 将验证码存储在全局验证码容器中
            validateCodeContainHelper.put(requestValidateCodeDTO.getUserId(), validateCode);   // 之前发送的验证码会自动失效.
        } else {
            // 发送失败
            throw new AppException("验证码发送失败,请稍后重试");
        }
        return standardMessage;
    }

    /**
     * 用户确认验证码
     *
     * @param confirmValidateCodeDTO
     * @return
     */
    @Override
    public StandardMessage confirmValidateCode(ConfirmValidateCodeDTO confirmValidateCodeDTO) {
        StandardMessage standardMessage = new StandardMessage();
        ValidateCode validateCode = validateCodeContainHelper.get(confirmValidateCodeDTO.getUserId());  // 在清除前进行获得
        // 无论是否成功均清除验证码
        validateCodeContainHelper.remove(confirmValidateCodeDTO.getUserId());
        if (validateCode != null && validateCode.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new AppException("验证码以失效,请重新获取");
        } else if (validateCode == null || !validateCode.getCode().equalsIgnoreCase(confirmValidateCodeDTO.getCode())) {
            throw new AppException("验证码错误,请重试");
        } else {
            // 验证成功
            standardMessage.setFlagCode(StandardMessage.VALIDATION_CODE_CONFRIM_SUCCESS_FLAG);
            standardMessage.setDescription(StandardMessage.VALIDATION_CODE_CONFRIM_SUCCESS_DESC);
        }
        return standardMessage;
    }
}
