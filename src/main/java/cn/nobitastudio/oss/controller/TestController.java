package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.oss.repo.DepartmentRepo;
import cn.nobitastudio.oss.repo.DoctorRepo;
import cn.nobitastudio.oss.scheduler.job.CheckRemindJob;
import cn.nobitastudio.oss.scheduler.job.EatDrugRemindJob;
import cn.nobitastudio.oss.scheduler.job.RemindJob;
import cn.nobitastudio.oss.service.inter.VisitService;
import cn.nobitastudio.oss.util.CommonUtil;
import cn.nobitastudio.oss.util.DateUtil;
import cn.nobitastudio.oss.vo.test.InitSchedulerJobVO;
import cn.nobitastudio.oss.vo.test.JobDetailVO;
import cn.nobitastudio.oss.vo.test.SimpleDepartmentVO;
import cn.nobitastudio.oss.vo.test.TriggerVO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
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
    @Inject
    private VisitService visitService;
    @Inject
    private DepartmentRepo departmentRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @ApiModelProperty("测试方法")
    @GetMapping
    public void test() {
        System.out.println(scheduler);
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
        jobDataMap.put(MESSAGE_TYPE, ENROLL_SUCCESS);
        jobDataMap.put(MOBILE, "15709932234");
        jobDataMap.put(HOSPITAL_NAME, "石河子大学医学院第一附属医院");
        jobDataMap.put(DIAGNOSIS_NAME, UUID.randomUUID().toString().substring(10, 15));
        jobDataMap.put(MEDICAL_CARD_ID, "325669");
        jobDataMap.put(DOCTOR_NAME, "谢萍");
        jobDataMap.put(DEPARTMENT, "泌尿科");
        jobDataMap.put(ENROLL_COST, "65.00元");
        jobDataMap.put(DIAGNOSIS_TIME, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        jobDataMap.put(DEPARTMENT_ADDRESS, "A区301(3楼)");
        jobDataMap.put(DIAGNOSIS_ORDER, "15");
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
        jobDataMap.put(MESSAGE_TYPE, ENROLL_SUCCESS);
        jobDataMap.put(MOBILE, jobDetailVO.getMobile());
        jobDataMap.put(HOSPITAL_NAME, jobDetailVO.getHospitalName());
        jobDataMap.put(DIAGNOSIS_NAME, jobDetailVO.getDiagnosisName());
        jobDataMap.put(MEDICAL_CARD_ID, jobDetailVO.getMedicalCardId());
        jobDataMap.put(DOCTOR_NAME, jobDetailVO.getDoctorName());
        jobDataMap.put(DEPARTMENT, jobDetailVO.getDepartment());
        jobDataMap.put(ENROLL_COST, jobDetailVO.getEnrollCost());
        jobDataMap.put(DIAGNOSIS_TIME, jobDetailVO.getDiagnosisTime());
        jobDataMap.put(DEPARTMENT_ADDRESS, jobDetailVO.getDepartmentAddress());
        jobDataMap.put(DIAGNOSIS_ORDER, jobDetailVO.getDiagnosisOrder());
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
            List<SimpleDepartmentVO> simpleDepartmentVOS = CommonUtil.castEntity(objects, SimpleDepartmentVO.class,String.class);
            return ServiceResult.success(simpleDepartmentVOS);
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }

    }

}
