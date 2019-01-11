package cn.nobitastudio.oss.scheduler.job;

import cn.nobitastudio.oss.util.SmsUtil;
import cn.nobitastudio.oss.model.vo.SmsSendResult;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;

import static cn.nobitastudio.oss.util.SmsUtil.*;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/04 14:48
 * @description
 */
public class RemindJob implements Job {

    static final Logger logger = LoggerFactory.getLogger(RemindJob.class);

    public static final Integer AHEAD_TIME = 2;  // 默认提前2小时

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SmsSendResult smsSendResult = SmsUtil.sendSms(SmsUtil.castJobDataMapToMap(jobExecutionContext.getJobDetail().getJobDataMap()));
    }

}
