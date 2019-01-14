package cn.nobitastudio.oss.config;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 17:57
 * @description 对quartz自动调度的支持
 */

import cn.nobitastudio.oss.helper.QuartzHelper;
import cn.nobitastudio.oss.scheduler.job.CheckValidateCodeContainerJob;
import org.quartz.*;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import static cn.nobitastudio.oss.scheduler.job.CheckValidateCodeContainerJob.DEFAULT_VALIDATE_CODE_SAVE_TIME;

/**
 * 定时任务调度配置类
 */
@Configuration
@ComponentScan
@EnableJpaRepositories("cn.nobitastudio.oss.repo")
@EntityScan("cn.nobitastudio.oss.entity")
public class SchedulerConfig implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerConfig.class);

    private Scheduler scheduler;

    @Value(value = "${oss.app.autoLaunchScan:true}")
    private Boolean autoLaunchScan;
    @Value(value = "${oss.app.scanInterval:3}")
    private Integer scanInterval;

    /*
     * 实例化SchedulerFactoryBean对象
     */
    @Bean(name = "schedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setQuartzProperties(quartzProperties());
        return factoryBean;
    }

    /*
     * 加载配置文件
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /*
     * quartz初始化监听器
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }

    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean
    public Scheduler scheduler() throws IOException {
        scheduler = schedulerFactoryBean().getScheduler();
        return scheduler;
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            if (autoLaunchScan) {
                Boolean scheduleLaunched = scheduler.isStarted();
                LOGGER.info("schedule是否开启：" + scheduleLaunched);
                if (!scheduleLaunched) {
                    scheduler.startDelayed(5);
                    LOGGER.info("启动schedule");
                }
                createCheckValidateCodeQuartzPlan();
            }
        } catch (SchedulerException e) {
            LOGGER.error("启动 Scheduler 失败: " + e.getMessage(), e);
        }
    }

    /**
     * 创建检查验证码容器的调度策略
     *
     * @throws SchedulerException
     */
    private void createCheckValidateCodeQuartzPlan() throws SchedulerException {
        TriggerKey checkValidateCodeTriggerKey = new TriggerKey(QuartzHelper.DEFAULT_CHECK_VALIDATE_CODE_CONTAINER_TRIGGER_NAME,
                QuartzHelper.DEFAULT_CHECK_VALIDATE_CODE_CONTAINER_TRIGGER_GROUP);
        JobKey checkValidateCodeJobKey = new JobKey(QuartzHelper.DEFAULT_CHECK_VALIDATE_CODE_CONTAINER_JOB_NAME,
                QuartzHelper.DEFAULT_CHECK_VALIDATE_CODE_CONTAINER_JOB_GROUP);
        Trigger checkValidateCodeTrigger = TriggerBuilder.newTrigger()
                .withIdentity(checkValidateCodeTriggerKey)
                .withSchedule(CalendarIntervalScheduleBuilder
                        .calendarIntervalSchedule()  // 日期调度类
                        .withIntervalInMinutes(scanInterval))   // 指定时间内进行查询
                .build();
        JobDetail checkValidateCodeJob = JobBuilder.newJob(CheckValidateCodeContainerJob.class)
                .withIdentity(checkValidateCodeJobKey)
                .build();
        if (scheduler.checkExists(checkValidateCodeJobKey) && scheduler.checkExists(checkValidateCodeTriggerKey)) {
            scheduler.unscheduleJob(checkValidateCodeTriggerKey);
        }
        scheduler.scheduleJob(checkValidateCodeJob, checkValidateCodeTrigger);
        LOGGER.info("启动验证码容器检查调度策略,调度频度：" + scanInterval + "分钟,最长保存时间：" + DEFAULT_VALIDATE_CODE_SAVE_TIME + "小时");
    }
}
