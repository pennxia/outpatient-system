package cn.nobitastudio.oss.scheduler.util;

import cn.nobitastudio.oss.entity.*;
import cn.nobitastudio.oss.model.enumeration.QuartzPlanType;
import cn.nobitastudio.oss.model.enumeration.RemindType;
import cn.nobitastudio.oss.scheduler.job.CheckOrderStateJob;
import cn.nobitastudio.oss.scheduler.job.RemindJob;
import cn.nobitastudio.oss.util.DateUtil;
import cn.nobitastudio.oss.util.SmsUtil;
import org.quartz.*;

import static cn.nobitastudio.oss.util.SmsUtil.*;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/11 11:50
 * @description
 */
public class QuartzUtil {

    // 生成挂号 JobKey  JobName: 提醒类型.visitId.序号 （JobType.visitId.diagnosisNo）    JobGroup: QuartzPlanType.userId.medicalCarId
    public static JobKey createDiagnosisRemindJobKey(User user, MedicalCard medicalCard, RemindType remindType, Visit visit, Integer diagnosisNo) {
        return new JobKey(remindType.name() + "." + visit.getId() + "." + diagnosisNo,
                QuartzPlanType.DIAGNOSIS_REMIND.name() + "." + user.getId() + "." + medicalCard.getId());
    }

    // 生成挂号 triggerKey  triggerName: 提醒类型_visitId_序号 （TriggerType.visitId.diagnosisNo）    triggerGroup: QuartzPlanType.userId.medicalCarId
    public static TriggerKey createDiagnosisRemindTriggerKey(User user, MedicalCard medicalCard, RemindType remindType, Visit visit, Integer diagnosisNo) {
        return new TriggerKey(remindType.name() + "." + visit.getId() + "." + diagnosisNo,
                QuartzPlanType.DIAGNOSIS_REMIND.name() + "." + user.getId() + "." + medicalCard.getId());
    }

    // 生成检查订单的triggerKey   jobName:  订单id       jobGroup: QuartzPlanType
    public static TriggerKey createCheckOrderStateTriggerKey(OSSOrder ossOrder) {
        return new TriggerKey(ossOrder.getId(), QuartzPlanType.CHECK_ORDER_STATE.name());
    }

    // 生成检查订单的 JobKey  jobName:  订单id       jobGroup: QuartzPlanType
    public static JobKey createCheckOrderStateJobKey(OSSOrder ossOrder) {
        return new JobKey(ossOrder.getId(), QuartzPlanType.CHECK_ORDER_STATE.name());
    }

    /**
     * 初始化 jobDetail
     *
     * @param visit
     * @param user
     * @param department
     * @return
     */
    public static JobDetail newDiagnosisRemindJobDetailInstance(OSSOrder ossOrder, Visit visit, User user, Department department, MedicalCard medicalCard, Doctor doctor, DiagnosisRoom diagnosisRoom, Integer diagnosisNo) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(ORDER_ID, ossOrder.getId());  // 执行前检查订单状态所需要的参数
        jobDataMap.putAll(initRegisterSuccessOrDiagnosisRemindSms(user, medicalCard, doctor, department, visit, diagnosisRoom, diagnosisNo, SmsUtil.DIAGNOSIS_REMIND));  // 发送短信的参数
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
                .startAt(DateUtil.formatLocalDateTimeToDate(visit.getDiagnosisTime().minusHours(RemindJob.AHEAD_HOUR_TIME))) // 就诊时间前两小时进行提醒
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()) // 使用SimpleScheduleBuilder创建simpleSchedule)
                .build();
        return trigger;
    }

    /**
     * 新建检查订单支付状态的Job
     *
     * @param ossOrder
     * @return
     */
    public static JobDetail newCheckOrderStateJob(OSSOrder ossOrder) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(CheckOrderStateJob.OSS_ORDER, ossOrder);
        JobDetail detail = JobBuilder.newJob(CheckOrderStateJob.class)
                .withIdentity(createCheckOrderStateJobKey(ossOrder))
                .usingJobData(jobDataMap)
                .build();
        return detail;
    }

    public static Trigger newCheckOrderStateTrigger(OSSOrder ossOrder) {
        Trigger trigger = TriggerBuilder.newTrigger() // 使用TriggerBuilder创建Trigger
                .withIdentity(createCheckOrderStateTriggerKey(ossOrder))  // 设置唯一标识符
                .startAt(DateUtil.formatLocalDateTimeToDate(ossOrder.getCreateTime().plusMinutes(CheckOrderStateJob.LEFT_PAY_TIME))) // 就诊时间前两小时进行提醒
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()) // 使用SimpleScheduleBuilder创建simpleSchedule)
                .build();
        return trigger;
    }
}
