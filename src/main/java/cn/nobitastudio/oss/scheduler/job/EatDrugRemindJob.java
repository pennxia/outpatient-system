package cn.nobitastudio.oss.scheduler.job;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 12:14
 * @description
 */
public class EatDrugRemindJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail detail = jobExecutionContext.getJobDetail();
        System.out.println("调度结束，" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("!!!!!!!!!!!!!!!!!!");
    }

}
