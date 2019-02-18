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
public class SchedulerConfig{

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerConfig.class);

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
         return schedulerFactoryBean().getScheduler();
    }
}
