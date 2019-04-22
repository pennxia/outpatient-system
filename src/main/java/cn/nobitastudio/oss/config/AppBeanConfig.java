package cn.nobitastudio.oss.config;

import cn.nobitastudio.oss.helper.QuartzHelper;
import cn.nobitastudio.oss.helper.SmsHelper;
import cn.nobitastudio.oss.helper.ValidateCodeContainHelper;
import cn.nobitastudio.oss.scheduler.job.CheckValidateCodeContainerJob;
import cn.nobitastudio.oss.util.SpringBeanUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cn.nobitastudio.oss.scheduler.job.CheckValidateCodeContainerJob.DEFAULT_VALIDATE_CODE_SAVE_TIME;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/12 12:33
 * @description
 */
@Configuration
public class AppBeanConfig implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppBeanConfig.class);

    @Value(value = "${oss.app.autoLaunchScan:true}")
    private Boolean autoLaunchScan;
    @Value(value = "${oss.app.scanInterval:3}")
    private Integer scanInterval;

    @Inject
    Scheduler scheduler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
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

    /**
     * Handle an application event.
     * <p>
     * 应用事件按以下顺序发送，作为您应用程序运行：
     * <p>
     * ApplicationStartedEvent发送一个t开始运行，但在除注册侦听器和初始化程序之外的任何处理之前。
     * <p>
     * 当在上下文中使用的环境已知时，但在创建上下文 之前发送ApplicationEnvironmentPreparedEvent。
     * <p>
     * ApplicationPreparedEvent只是在刷新开始之前发送，但是在bean定义加载之后。
     * <p>
     * 刷新后发送ApplicationReadyEvent，并处理任何相关的回调以指示应用程序已准备好 服务请求。
     * <p>
     * 如果启动时出现异常，则会发送ApplicationFailedEvent。
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        startSchedule();
        LOGGER.info("OSS 启动完成");
    }

    /**
     * 将context 注入到普通的util中
     */
    private void injectContext(ApplicationContext applicationContext) {
        SpringBeanUtil.setApplicationContext(applicationContext);
        LOGGER.info("注入 context 成功");
    }

    /**
     * 启动调度
     */
    private void startSchedule() {
        try {
            if (autoLaunchScan) {
                Boolean scheduleLaunched = scheduler.isStarted();
                LOGGER.info("schedule是否开启：" + scheduleLaunched);
                if (!scheduleLaunched) {
                    scheduler.startDelayed(5);
                    LOGGER.info("启动 schedule");
                }
//                createCheckValidateCodeQuartzPlan();
//                LOGGER.info("启动 Scheduler Job计划成功");  // 已经采用redis来代替.
            }
        } catch (SchedulerException e) {
            LOGGER.error("启动 Scheduler 失败: " + e.getMessage(), e);
        }
    }

    // 启动时调用
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        injectContext(applicationContext);
    }
}
