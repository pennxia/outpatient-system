package cn.nobitastudio.oss.scheduler.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 12:14
 * @description
 */
public class EatDrugRemindJob implements Job {
    static final Logger LOGGER = LoggerFactory.getLogger(EatDrugRemindJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        LOGGER.info(this.getClass().getName() + "---" + LocalDateTime.now());
        LOGGER.info(jobDataMap.getString("mobile"));
        sendSms(jobDataMap.getString("mobile"));
    }

    /**
     * 这种提醒短信的实现
     * @param mobile
     */
    private void sendSms(String mobile) {
        LOGGER.info("send EatDrugRemindJob-sms to:" + mobile);
    }

}
