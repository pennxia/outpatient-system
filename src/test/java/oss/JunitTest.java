package oss;

import cn.nobitastudio.oss.OSSApplication;
import cn.nobitastudio.oss.model.enumeration.Channel;
import cn.nobitastudio.oss.scheduler.job.EatDrugRemindJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = OSSApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class JunitTest {

    static Logger logger = LoggerFactory.getLogger(JunitTest.class);

    @Test
    public void show() throws SchedulerException, InterruptedException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        scheduler.start();
        Trigger trigger = TriggerBuilder.newTrigger() // 使用TriggerBuilder创建Trigger
                .withIdentity("trigger1", "group1")
                .startAt(new Date(LocalDateTime.of(2019,1,3,12,52,59).minusSeconds(10).toInstant(ZoneOffset.of("+8")).toEpochMilli()))
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()) // 使用SimpleScheduleBuilder创建simpleSchedule) // 最多执行100次,此处需要注意，不包括第一次执行的
                .build();
        JobDetail detail = JobBuilder.newJob(EatDrugRemindJob.class).build();
        System.out.println("开始调度，" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduler.scheduleJob(detail,trigger);
        Thread.sleep(5000000);

        //System.out.println(LocalDateTime.now().plusSeconds(20).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        // 时间戳 localdatetime 互转
//        Long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
//        LocalDateTime time2 =LocalDateTime.ofEpochSecond(timestamp/1000,0,ZoneOffset.ofHours(8));
    }

    @Test
    public void test2() {
        Map<String,String> map = new HashMap<>();
        map.put("1","2");
        map.put("2","3");
        map.put("1","5");
        logger.info(map.toString());
    }

    @Test
    public void test3() {
        System.out.println(Channel.OSS_ANDROID_APP.ordinal());
        System.out.println(Channel.OSS_WEB_APP.ordinal());
        System.out.println(Channel.OSS_HOSPITAL_LOCAL.ordinal());
        System.out.println(Channel.OSS_OTHER.ordinal());
        System.out.println(Channel.OSS_IOS_APP.ordinal());
    }


}

