package cn.nobitastudio.oss.scheduler.job;

import cn.nobitastudio.oss.helper.ValidateCodeContainHelper;
import cn.nobitastudio.oss.util.DateUtil;
import cn.nobitastudio.oss.util.SpringBeanUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/14 16:53
 * @description 检查验证码容器中长期未使用的验证码, 将其清除,设计为3分钟调度一次
 */
public class CheckValidateCodeContainerJob implements Job {

    public static final Integer DEFAULT_VALIDATE_CODE_SAVE_TIME = 5;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ValidateCodeContainHelper validateCodeContainHelper = SpringBeanUtil.getBean(ValidateCodeContainHelper.class);
        validateCodeContainHelper.forEach((key, value) -> {
            if (value.getExpireTime().plusHours(DEFAULT_VALIDATE_CODE_SAVE_TIME).isBefore(LocalDateTime.now())) {
                //  超过验证时间5小时（默认时间）未进行认证
                validateCodeContainHelper.remove(key);
            }
        });
    }
}
