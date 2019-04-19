package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.oss.cache.RedisHelper;
import cn.nobitastudio.oss.entity.MedicalCard;
import cn.nobitastudio.oss.helper.ValidateCodeContainHelper;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.oss.model.dto.ConfirmValidateCodeDTO;
import cn.nobitastudio.oss.model.dto.RequestValidateCodeDTO;
import cn.nobitastudio.oss.model.dto.StandardInfo;
import cn.nobitastudio.oss.model.dto.ValidateCode;
import cn.nobitastudio.oss.model.error.ErrorCode;
import cn.nobitastudio.oss.model.vo.SmsSendResult;
import cn.nobitastudio.oss.model.vo.StandardMessage;
import cn.nobitastudio.oss.repo.MedicalCardRepo;
import cn.nobitastudio.oss.repo.UserRepo;
import cn.nobitastudio.oss.service.inter.ValidateService;
import cn.nobitastudio.oss.helper.SmsHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/14 14:06
 * @description
 */
@Service
public class ValidateServiceImpl implements ValidateService {

    //    @Inject
//    private ValidateCodeContainHelper validateCodeContainHelper; // 验证码存储容器使用redis代替
    @Inject
    private SmsHelper smsHelper;
    @Inject
    private ExecutorService executorService;
    @Inject
    private RedisHelper redisHelper;
    @Value(value = "${oss.app.captcha.expireTime:3600}")
    private long captchaExpireTime;  // 验证码的保存时间 默认一个小时

    @Override
    public StandardInfo requestValidateCode(RequestValidateCodeDTO requestValidateCodeDTO) {
        // 生成验证码
        ValidateCode validateCode = new ValidateCode();
        // 进行发送
        // 直接给指定手机号发送验证码
        SmsSendResult smsSendResult = smsHelper.sendSms(smsHelper.initRequestValidateCodeSms
                (requestValidateCodeDTO.getMobile(), requestValidateCodeDTO.getSmsMessageType(), validateCode));
        if (smsSendResult.getResult().equals(Boolean.TRUE)) {
            // 发送成功
            //保存到redis
            redisHelper.set(requestValidateCodeDTO.getMobile(), validateCode, captchaExpireTime, TimeUnit.SECONDS);
            return new StandardInfo("发送成功");
        } else {
            // 发送失败
            throw new AppException("验证码发送失败,请稍后重试", ErrorCode.NOT_FIND_ORDER);
        }
    }

    /**
     * 用户确认验证码
     *
     * @param confirmValidateCodeDTO
     * @return
     */
    @Override
    public StandardInfo confirmValidateCode(ConfirmValidateCodeDTO confirmValidateCodeDTO) {
        ValidateCode validateCode = redisHelper.get(confirmValidateCodeDTO.getMobile(), ValidateCode.class);
//        redisHelper.del(confirmValidateCodeDTO.getMobile());  // 验证过的验证码一定清除不管是否正确 短信验证码不用删除
        if (validateCode == null || !validateCode.getCode().equals(confirmValidateCodeDTO.getCode())) {
            // 验证码错误 ：因为请求验证码或者验证码保存时间超时:3600秒 输入错误
            throw new AppException("验证码错误", ErrorCode.CAPTCHA_ERROR);
        } else if (validateCode.getExpireTime().isBefore(LocalDateTime.now())) {
            // 验证码已过有效期
            redisHelper.del(confirmValidateCodeDTO.getMobile());
            throw new AppException("验证码已过时", ErrorCode.CAPTCHA_EXPIRE);
        } else {
            redisHelper.del(confirmValidateCodeDTO.getMobile());
            return new StandardInfo("认证成功");
        }
    }
}
