package cn.nobitastudio.oss.scheduler.util;

import cn.nobitastudio.oss.entity.*;
import cn.nobitastudio.oss.model.enumeration.RemindType;
import cn.nobitastudio.oss.scheduler.job.RemindJob;
import cn.nobitastudio.oss.util.DateUtil;
import org.quartz.*;

import static cn.nobitastudio.oss.util.SmsUtil.*;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/11 11:50
 * @description
 */
public class QuartzUtil {

    // 生成挂号 JobKey  JobName: 提醒类型_visitId_序号 （JobType_visitId_diagnosisNo）    JobGroup: userId_medicalCarId
    public static JobKey createDiagnosisRemindJobKey(User user, MedicalCard medicalCard, RemindType remindType, Visit visit, Integer diagnosisNo) {
        return new JobKey(remindType.name() + "_" + visit.getId() + "_" + diagnosisNo, user.getId() + "_" + medicalCard.getId());
    }

    // 生成挂号 triggerKey  triggerName: 提醒类型_visitId_序号 （TriggerType_visitId_diagnosisNo）    triggerGroup: userId_medicalCarId
    public static TriggerKey createDiagnosisRemindTriggerKey(User user, MedicalCard medicalCard, RemindType remindType, Visit visit, Integer diagnosisNo) {
        return new TriggerKey(remindType.name() + "_" + visit.getId() + "_" + diagnosisNo, user.getId() + "_" + medicalCard.getId());
    }

    /**
     * 初始化 jobDetail
     *
     * @param visit
     * @param user
     * @param department
     * @return
     */
    public static JobDetail newDiagnosisRemindJobDetailInstance(Visit visit, User user, Department department, MedicalCard medicalCard, Doctor doctor, DiagnosisRoom diagnosisRoom, Integer diagnosisNo) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(initRegisterSuccessOrDiagnosisRemindSms(user, medicalCard, doctor, department, visit, diagnosisRoom, diagnosisNo));
        JobDetail detail = JobBuilder.newJob(RemindJob.class)
                .withIdentity(createDiagnosisRemindJobKey(user, medicalCard, RemindType.DIAGNOSIS_REMIND, visit, diagnosisNo))
                .usingJobData(jobDataMap)
                .build();
        return detail;
    }

    /**
     * 初始化挂号成功后未来就诊提醒的trigger  默认就诊时间的前2小时进行提醒
     *
     * @param medicalCard
     * @param visit
     * @return
     */
    public static Trigger newDiagnosisRemindTriggerInstance(Visit visit, User user, MedicalCard medicalCard, Integer diagnosisNo) {
        Trigger trigger = TriggerBuilder.newTrigger() // 使用TriggerBuilder创建Trigger
                .withIdentity(createDiagnosisRemindTriggerKey(user, medicalCard, RemindType.DIAGNOSIS_REMIND, visit, diagnosisNo))  // 设置唯一标识符
                .startAt(DateUtil.formatLocalDateTimeToDate(visit.getDiagnosisTime().minusHours(RemindJob.AHEAD_TIME))) // 就诊时间前两小时进行提醒
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()) // 使用SimpleScheduleBuilder创建simpleSchedule)
                .build();
        return trigger;
    }
}
