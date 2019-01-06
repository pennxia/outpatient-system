package cn.nobitastudio.oss.scheduler.job;

import cn.nobitastudio.oss.util.SmsSendUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 12:14
 * @description
 */
public class CheckRemindJob implements Job {

    static final Logger LOGGER = LoggerFactory.getLogger(CheckRemindJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        sendSms(jobDataMap.getString("mobile"));
    }

    /**
     * 这种提醒短信的实现
     * @param mobile
     */
    private void sendSms(String mobile) {

    }
}
