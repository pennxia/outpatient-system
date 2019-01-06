package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Doctor;
import cn.nobitastudio.oss.repo.DoctorRepo;
import cn.nobitastudio.oss.scheduler.job.CheckRemindJob;
import cn.nobitastudio.oss.scheduler.job.EatDrugRemindJob;
import cn.nobitastudio.oss.scheduler.job.RemindJob;
import cn.nobitastudio.oss.util.SmsSendUtil;
import io.swagger.annotations.ApiModelProperty;
import org.quartz.*;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import static cn.nobitastudio.oss.scheduler.job.RemindJob.*;
import static cn.nobitastudio.oss.util.SmsSendUtil.ENROLL_SUCCESS;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 17:59
 * @description
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Inject
    private Scheduler scheduler;
    @Inject
    private DoctorRepo doctorRepo;

    @ApiModelProperty("测试方法")
    @GetMapping
    public ServiceResult<String> test() {

        System.out.println(scheduler);

        return ServiceResult.success(scheduler.toString());
    }

    @ApiModelProperty("测试方法")
    @GetMapping("/2")
    public void test2() throws SchedulerException {
        Trigger trigger = TriggerBuilder.newTrigger() // 使用TriggerBuilder创建Trigger
                .withIdentity("trigger3", "group3")
                .startAt(new Date(LocalDateTime.now().plusSeconds(10).toInstant(ZoneOffset.of("+8")).toEpochMilli()))
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()) // 使用SimpleScheduleBuilder创建simpleSchedule) // 最多执行100次,此处需要注意，不包括第一次执行的
                .build();
        JobDetail detail = JobBuilder.newJob(EatDrugRemindJob.class)
                .usingJobData("mobile","1111111111")
                .build();
        System.out.println("开始调度，" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduler.scheduleJob(detail,trigger);
    }

    @ApiModelProperty("测试方法")
    @GetMapping("/3")
    public void test3() throws SchedulerException {
        Trigger trigger = TriggerBuilder.newTrigger() // 使用TriggerBuilder创建Trigger
                .withIdentity("trigger1", "group1")
                .startAt(new Date(LocalDateTime.now().plusSeconds(10).toInstant(ZoneOffset.of("+8")).toEpochMilli()))
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()) // 使用SimpleScheduleBuilder创建simpleSchedule) // 最多执行100次,此处需要注意，不包括第一次执行的
                .build();
        JobDetail detail = JobBuilder.newJob(EatDrugRemindJob.class)
                .usingJobData("mobile","2222222")
                .build();
        System.out.println("开始调度，" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduler.scheduleJob(detail,trigger);
    }

    @ApiModelProperty("测试方法")
    @GetMapping("/4")
    public void test4() throws SchedulerException {
        Trigger trigger = TriggerBuilder.newTrigger() // 使用TriggerBuilder创建Trigger
                .withIdentity("trigger2", "group2")
                .startAt(new Date(LocalDateTime.now().plusSeconds(10).toInstant(ZoneOffset.of("+8")).toEpochMilli()))
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()) // 使用SimpleScheduleBuilder创建simpleSchedule) // 最多执行100次,此处需要注意，不包括第一次执行的
                .build();
        JobDetail detail = JobBuilder.newJob(CheckRemindJob.class)
                .usingJobData("mobile","33333333")
                .build();
        System.out.println("开始调度，" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduler.scheduleJob(detail,trigger);
    }

    @ApiModelProperty("测试方法")
    @GetMapping("/5")
    public void test5() throws SchedulerException {
        Trigger trigger = TriggerBuilder.newTrigger() // 使用TriggerBuilder创建Trigger
                .withIdentity("trigger2", "group2")
                .startAt(new Date(LocalDateTime.now().plusSeconds(120).toInstant(ZoneOffset.of("+8")).toEpochMilli()))
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()) // 使用SimpleScheduleBuilder创建simpleSchedule) // 最多执行100次,此处需要注意，不包括第一次执行的
                .build();
        JobDetail detail = JobBuilder.newJob(CheckRemindJob.class)
                .usingJobData("mobile","33333333")
                .build();
        System.out.println("开始调度，" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduler.scheduleJob(detail,trigger);
    }

    @ApiModelProperty("测试RemindJob")
    @GetMapping("/remindJob")
    public void test6() throws SchedulerException {
        Trigger trigger = TriggerBuilder.newTrigger() // 使用TriggerBuilder创建Trigger
                .withIdentity(UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .startAt(new Date(LocalDateTime.now().plusSeconds(7).toInstant(ZoneOffset.of("+8")).toEpochMilli()))
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()) // 使用SimpleScheduleBuilder创建simpleSchedule) // 最多执行100次,此处需要注意，不包括第一次执行的
                .build();
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(MESSAGE_TYPE, ENROLL_SUCCESS);
        jobDataMap.put(MOBILE, "15709932234");
        jobDataMap.put(HOSPITAL_NAME, "石河子大学医学院第一附属医院");
        jobDataMap.put(DIAGNOSIS_NAME, UUID.randomUUID().toString().substring(10,15));
        jobDataMap.put(MEDICAL_CARD_NO, "325669");
        jobDataMap.put(DOCTOR, "谢萍");
        jobDataMap.put(DEPARTMENT, "泌尿科");
        jobDataMap.put(ENROLL_COST, "65.00元");
        jobDataMap.put(DIAGNOSIS_TIME,LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        jobDataMap.put(DEPARTMENT_ADDRESS, "A区301(3楼)");
        jobDataMap.put(DIAGNOSIS_ORDER, "15");
        JobDetail detail = JobBuilder.newJob(RemindJob.class)
                .usingJobData(jobDataMap)
                .build();
        System.out.println("开始调度，" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduler.scheduleJob(detail,trigger);
    }


}
