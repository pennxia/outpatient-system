package cn.nobitastudio.oss.scheduler.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 12:02
 * @description
 */
public class DiagnosisRemindJob implements Job {
    static final Logger LOGGER = LoggerFactory.getLogger(DiagnosisRemindJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        sendSms(jobDataMap.getString("mobile"));
    }

    /**
     * 这种提醒短信的实现
     * @param mobile
     */
    private void sendSms(String mobile) {
        LOGGER.info("send DiagnosisRemindJob-sms to:" + mobile);
    }

}
