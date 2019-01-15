package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.oss.entity.Test;
import cn.nobitastudio.oss.helper.QuartzHelper;
import cn.nobitastudio.oss.helper.ValidateCodeContainHelper;
import cn.nobitastudio.oss.model.dto.RequestValidateCodeDTO;
import cn.nobitastudio.oss.model.enumeration.SmsMessageType;
import cn.nobitastudio.oss.model.vo.StandardMessage;
import cn.nobitastudio.oss.repo.DepartmentRepo;
import cn.nobitastudio.oss.repo.DoctorRepo;
import cn.nobitastudio.oss.repo.OSSOrderRepo;
import cn.nobitastudio.oss.repo.TestRepo;
import cn.nobitastudio.oss.scheduler.job.CheckRemindJob;
import cn.nobitastudio.oss.scheduler.job.CheckValidateCodeContainerJob;
import cn.nobitastudio.oss.scheduler.job.EatDrugRemindJob;
import cn.nobitastudio.oss.scheduler.job.RemindJob;
import cn.nobitastudio.oss.service.inter.TestService;
import cn.nobitastudio.oss.service.inter.ValidateService;
import cn.nobitastudio.oss.service.inter.VisitService;
import cn.nobitastudio.oss.util.CommonUtil;
import cn.nobitastudio.oss.util.DateUtil;
import cn.nobitastudio.oss.model.test.InitSchedulerJobVO;
import cn.nobitastudio.oss.model.test.JobDetailVO;
import cn.nobitastudio.oss.model.test.SimpleDepartmentVO;
import cn.nobitastudio.oss.model.test.TriggerVO;
import cn.nobitastudio.oss.util.SnowFlakeUtil;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static cn.nobitastudio.oss.helper.SmsHelper.*;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 17:59
 * @description
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value(value = "${oss.app.scanInterval:3}")
    private Integer scanInterval;
    @Value(value = "${oss.app.scanInterval:5}")
    private Integer defaultValidateCodeSaveTime;

    @Inject
    private Scheduler scheduler;
    @Inject
    private DoctorRepo doctorRepo;
    @Inject
    private VisitService visitService;
    @Inject
    private DepartmentRepo departmentRepo;
    @Inject
    private TestRepo testRepo;
    @Inject
    private TestService testService;
    @Inject
    private OSSOrderRepo ossOrderRepo;
    @Inject
    private ValidateService validateService;
    @Inject
    private ValidateCodeContainHelper validateCodeContainHelper;


    @ApiModelProperty("测试方法")
    @GetMapping
    public void test() {
        System.out.println(validateCodeContainHelper);
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
                .usingJobData("mobile", "1111111111")
                .build();
        System.out.println("开始调度，" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduler.scheduleJob(detail, trigger);
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
                .usingJobData("mobile", "2222222")
                .build();
        System.out.println("开始调度，" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduler.scheduleJob(detail, trigger);
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
                .usingJobData("mobile", "33333333")
                .build();
        System.out.println("开始调度，" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduler.scheduleJob(detail, trigger);
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
                .usingJobData("mobile", "33333333")
                .build();
        System.out.println("开始调度，" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduler.scheduleJob(detail, trigger);
    }

    @ApiModelProperty("测试RemindJob")
    @GetMapping("/remindJob")
    public void test6() throws SchedulerException {
        Trigger trigger = TriggerBuilder.newTrigger() // 使用TriggerBuilder创建Trigger
                .withIdentity(UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .startAt(DateUtil.formatLocalDateTimeToDate(LocalDateTime.now().plusSeconds(7)))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()) // 使用SimpleScheduleBuilder创建simpleSchedule) // 最多执行100次,此处需要注意，不包括第一次执行的
                .build();
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(MESSAGE_TYPE, REGISTER_SUCCESS_HAVE_PAY);
        jobDataMap.put(MOBILE, "15709932234");
        jobDataMap.put(HOSPITAL_NAME, "石河子大学医学院第一附属医院");
        jobDataMap.put(MEDICAL_CARD_OWNER, UUID.randomUUID().toString().substring(10, 15));
        jobDataMap.put(MEDICAL_CARD_ID, "325669");
        jobDataMap.put(DOCTOR_NAME, "谢萍");
        jobDataMap.put(DEPARTMENT_NAME, "泌尿科");
        jobDataMap.put(REGISTER_COST, "65.00元");
        jobDataMap.put(DIAGNOSIS_TIME, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        jobDataMap.put(DIAGNOSIS_ROOM, "A区301(3楼)");
        jobDataMap.put(DIAGNOSIS_NO, "15");
        JobDetail jobDetail = JobBuilder.newJob(RemindJob.class)
                .usingJobData(jobDataMap)
                .build();
        System.out.println("开始调度，" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @ApiOperation("测试从visit里查询数据,初始化调度器的job")
    @PutMapping("/quartz")
    public ServiceResult<String> quartz(@RequestBody InitSchedulerJobVO initSchedulerJobVO) {
        try {
            CommonUtil.checkObjectFieldIsNull(initSchedulerJobVO.getTriggerVO());
            CommonUtil.checkObjectFieldIsNull(initSchedulerJobVO.getJobDetailVO());
            Trigger trigger = newTriggerInstance(initSchedulerJobVO.getTriggerVO());
            JobDetail jobDetail = newJobDetailInstance(initSchedulerJobVO.getJobDetailVO());
            scheduler.scheduleJob(jobDetail, trigger);
            return ServiceResult.success("调度成功");
        } catch (Exception e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    /**
     * 初始化 jobDetail
     *
     * @param jobDetailVO
     * @return
     */
    public JobDetail newJobDetailInstance(JobDetailVO jobDetailVO) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(MESSAGE_TYPE, REGISTER_SUCCESS_HAVE_PAY);
        jobDataMap.put(MOBILE, jobDetailVO.getMobile());
        jobDataMap.put(HOSPITAL_NAME, jobDetailVO.getHospitalName());
        jobDataMap.put(MEDICAL_CARD_OWNER, jobDetailVO.getDiagnosisName());
        jobDataMap.put(MEDICAL_CARD_ID, jobDetailVO.getMedicalCardId());
        jobDataMap.put(DOCTOR_NAME, jobDetailVO.getDoctorName());
        jobDataMap.put(DEPARTMENT_NAME, jobDetailVO.getDepartment());
        jobDataMap.put(REGISTER_COST, jobDetailVO.getEnrollCost());
        jobDataMap.put(DIAGNOSIS_TIME, jobDetailVO.getDiagnosisTime());
        jobDataMap.put(DIAGNOSIS_ROOM, jobDetailVO.getDepartmentAddress());
        jobDataMap.put(DIAGNOSIS_NO, jobDetailVO.getDiagnosisOrder());
        JobDetail detail = JobBuilder.newJob(RemindJob.class)
                .usingJobData(jobDataMap)
                .build();
        return detail;
    }

    /**
     * 初始化trigger
     *
     * @param triggerVO
     * @return
     */
    public Trigger newTriggerInstance(TriggerVO triggerVO) {
        Trigger trigger = TriggerBuilder.newTrigger() // 使用TriggerBuilder创建Trigger
                .withIdentity(triggerVO.getName(), triggerVO.getGroup())
                .startAt(DateUtil.formatLocalDateTimeToDate(triggerVO.getLocalDateTime()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()) // 使用SimpleScheduleBuilder创建simpleSchedule) // 最多执行100次,此处需要注意，不包括第一次执行的
                .build();
        return trigger;
    }

    @ApiOperation("测试ORM")
    @GetMapping("/{id}/orm")
    public ServiceResult<List<SimpleDepartmentVO>> get(@PathVariable(name = "id") Integer id) throws Exception {
        List<Object[]> objects = departmentRepo.findSimpleDepartments(id);
        try {
            List<SimpleDepartmentVO> simpleDepartmentVOS = CommonUtil.castEntity(objects, SimpleDepartmentVO.class, String.class);
            return ServiceResult.success(simpleDepartmentVOS);
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("测试并发获取id")
    @GetMapping("/concurrent-id")
    public void testConcurrent() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                Long id = SnowFlakeUtil.getUniqueId();
                testRepo.save(new Test(id.toString()));
            }).start();
        }
        while (true) {
            System.out.println("总数是：" + testRepo.countAll());
            Thread.sleep(2000);
        }
    }

    @ApiOperation("测试并发调度接口")
    @GetMapping("/concurrent-inter")
    public void testConcurrentInter() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                testService.test();
            }).start();
        }
    }

    @ApiOperation("测试config")
    @GetMapping("/configuration2")
    public ServiceResult<String> testConf2() {
        return ServiceResult.success(testService.testConf());
    }

    @ApiModelProperty("测试bean 获取")
    @GetMapping("/bean")
    public void testBean() {
        System.out.println(ossOrderRepo);
    }

    @ApiOperation("测试另一种调度器")
    @GetMapping("/test-quartz")
    public void testQuartz() throws SchedulerException {
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
        logger.info("启动验证码容器检查调度策略,调度频度：" + scanInterval + "分钟,最长保存时间：" + defaultValidateCodeSaveTime + "小时");
        scheduler.scheduleJob(checkValidateCodeJob, checkValidateCodeTrigger);
    }

    @ApiOperation("测试另一种调度器")
    @GetMapping("/unschedule-test-quartz")
    public void unschedule() throws SchedulerException {
        scheduler.unscheduleJob(new TriggerKey(QuartzHelper.DEFAULT_CHECK_VALIDATE_CODE_CONTAINER_TRIGGER_NAME,
                QuartzHelper.DEFAULT_CHECK_VALIDATE_CODE_CONTAINER_TRIGGER_GROUP));
    }

    @ApiOperation("用户请求短息验证码")
    @GetMapping("/test-validate")
    public ServiceResult testValidate() {
        try {
            return ServiceResult.success(validateService.requestValidateCode(new RequestValidateCodeDTO(9, SmsMessageType.ENROLL)));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }

    }

}