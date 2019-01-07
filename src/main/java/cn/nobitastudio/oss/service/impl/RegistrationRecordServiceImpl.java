package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.*;
import cn.nobitastudio.oss.repo.*;
import cn.nobitastudio.oss.scheduler.job.RemindJob;
import cn.nobitastudio.oss.scheduler.job.TestJob;
import cn.nobitastudio.oss.service.inter.RegistrationRecordService;
import cn.nobitastudio.oss.util.DateUtil;
import cn.nobitastudio.oss.vo.test.JobDetailVO;
import cn.nobitastudio.oss.vo.test.TriggerVO;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static cn.nobitastudio.oss.scheduler.job.RemindJob.*;
import static cn.nobitastudio.oss.scheduler.job.RemindJob.DIAGNOSIS_ORDER;
import static cn.nobitastudio.oss.util.SmsSendUtil.ENROLL_SUCCESS;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/06 15:13
 * @description
 */
@Service
public class RegistrationRecordServiceImpl implements RegistrationRecordService {

    static Logger logger = LoggerFactory.getLogger(RegistrationRecordServiceImpl.class);

    private static final String DELETE_SUCCESS = "挂号记录信息删除成功";
    private static final String SAVE_OR_UPDATE_SUCCESS = "挂号记录信息添加或修改成功";

    @Inject
    private RegistrationRecordRepo registrationRecordRepo;
    @Inject
    private VisitRepo visitRepo;
    @Inject
    private DoctorRepo doctorRepo;
    @Inject
    private DepartmentRepo departmentRepo;
    @Inject
    private UserRepo userRepo;
    @Inject
    private MedicalCardRepo medicalCardRepo;
    @Inject
    private Scheduler scheduler;

    /**
     * 查询指定id挂号记录信息
     *
     * @param id 挂号记录id
     * @return
     */
    @Override
    public RegistrationRecord getById(Integer id) {
        return registrationRecordRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定id的挂号记录"));
    }

    /**
     * 查询所有挂号记录,结果进行分页
     *
     * @param registrationRecord
     * @param pager              分页参数
     * @return
     */
    @Override
    public PageImpl<RegistrationRecord> getAll(RegistrationRecord registrationRecord, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(), pager.getLimit(), Sort.by(Sort.Direction.DESC, "createTime"));
        Page<RegistrationRecord> registrationRecords = registrationRecordRepo.findAll(SpecificationBuilder.toSpecification(registrationRecord), pageable);
        return new PageImpl<>(registrationRecords.getContent(), pageable, registrationRecords.getTotalElements());
    }

    /**
     * 删除指定挂号记录信息
     *
     * @param id 指定挂号记录id
     * @return
     */
    @Override
    public String delete(Integer id) {
        registrationRecordRepo.delete(registrationRecordRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定id的挂号记录")));
        return DELETE_SUCCESS;
    }

    /**
     * 新增或更新挂号记录
     *
     * @param registrationRecord 待新增或更新的挂号记录
     * @return
     */
    @Override
    public RegistrationRecord save(RegistrationRecord registrationRecord) {
        return registrationRecordRepo.save(registrationRecord);
    }

    /**
     * 用户进行挂号操作
     *
     * @param registrationRecord
     * @return
     */
    @Override
    public RegistrationRecord register(RegistrationRecord registrationRecord) {
        // 待实现

        // 保存 RegistrationRecord

        // 挂号成功,发送通知短信.
        User user = userRepo.findById(registrationRecord.getUserId()).orElseThrow(() -> new AppException("未查找到指定用户"));
        MedicalCard medicalCard = medicalCardRepo.findById(registrationRecord.getMedicalCardId()).orElseThrow(() -> new AppException("未查找到指定诊疗卡"));
        Visit visit = visitRepo.findById(registrationRecord.getVisitId()).orElseThrow(() -> new AppException("未查找到指定的号源信息"));
        Doctor doctor = doctorRepo.findById(visit.getDoctorId()).orElseThrow(() -> new AppException("未查找到指定医生信息"));
        Department department = departmentRepo.findById(doctor.getDepartmentId()).orElseThrow(() -> new AppException("未查找到指定科室信息"));
        Trigger trigger = newTriggerInstance(medicalCard, visit);
        JobDetail jobDetail = newJobDetailInstance(visit, user, department, medicalCard, doctor);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("调度成功,开始调度时间:" + DateUtil.formatLocalDateTimeToString(visit.getDiagnosisTime()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        // 储存未来就医提醒 job
        visit.setDiagnosisTime(visit.getDiagnosisTime().minusHours(2));
        department.setAddress("测试测试");
        Trigger trigger2 = newTriggerInstance(medicalCard, visit);
        JobDetail jobDetail2 = newJobDetailInstance(visit, user, department, medicalCard, doctor);
        try {
            scheduler.scheduleJob(jobDetail2, trigger2);
            logger.info("调度成功,开始调度时间:" + DateUtil.formatLocalDateTimeToString(visit.getDiagnosisTime()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化 jobDetail
     *
     * @param visit
     * @param user
     * @param department
     * @return
     */
    public JobDetail newJobDetailInstance(Visit visit, User user, Department department, MedicalCard medicalCard, Doctor doctor) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(MESSAGE_TYPE, ENROLL_SUCCESS);
        jobDataMap.put(MOBILE, medicalCard.getOwnerMobile());
        jobDataMap.put(HOSPITAL_NAME, "石河子大学医学院第一附属医院");
        jobDataMap.put(DIAGNOSIS_NAME, medicalCard.getOwnerName());
        jobDataMap.put(MEDICAL_CARD_ID, medicalCard.getId());
        jobDataMap.put(DOCTOR_NAME, doctor.getName());
        jobDataMap.put(DEPARTMENT, department.getName());
        jobDataMap.put(ENROLL_COST, "11.00元");
        jobDataMap.put(DIAGNOSIS_TIME, DateUtil.formatLocalDateTimeToString(visit.getDiagnosisTime()));
        jobDataMap.put(DEPARTMENT_ADDRESS, department.getAddress());
        jobDataMap.put(DIAGNOSIS_ORDER, "12");
        JobDetail detail = JobBuilder.newJob(TestJob.class)
                .usingJobData(jobDataMap)
                .build();
        return detail;
    }

    public Trigger newTriggerInstance(MedicalCard medicalCard, Visit visit) {
        Trigger trigger = TriggerBuilder.newTrigger() // 使用TriggerBuilder创建Trigger
                .startAt(DateUtil.formatLocalDateTimeToDate(visit.getDiagnosisTime()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()) // 使用SimpleScheduleBuilder创建simpleSchedule) // 最多执行100次,此处需要注意，不包括第一次执行的
                .build();
        return trigger;
    }
}
