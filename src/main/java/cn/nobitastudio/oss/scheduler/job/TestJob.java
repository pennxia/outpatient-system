package cn.nobitastudio.oss.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/07 11:45
 * @description
 */
public class TestJob implements Job {
    static Logger logger = LoggerFactory.getLogger(TestJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("测试执行");
    }
}
