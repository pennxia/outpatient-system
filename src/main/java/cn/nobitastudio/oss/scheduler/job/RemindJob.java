package cn.nobitastudio.oss.scheduler.job;

import cn.nobitastudio.oss.util.SmsSendUtil;
import cn.nobitastudio.oss.vo.SendSmsResult;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/04 14:48
 * @description
 */
public class RemindJob implements Job {

    static final Logger logger = LoggerFactory.getLogger(RemindJob.class);

    public static final String MESSAGE_TYPE = "messageType";
    public static final String MOBILE = "mobile";
    public static final String HOSPITAL_NAME = "hospitalName";
    public static final String DIAGNOSIS_NAME = "diagnosisName";
    public static final String MEDICAL_CARD_NO = "medicalCardNo";
    public static final String DOCTOR = "doctor";
    public static final String DEPARTMENT = "medicalCardNo";
    public static final String ENROLL_COST = "enrollCost";
    public static final String DIAGNOSIS_TIME = "diagnosisTime";
    public static final String DEPARTMENT_ADDRESS = "departmentAddress";
    public static final String DIAGNOSIS_ORDER = "diagnosisOrder";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SendSmsResult sendSmsResult = sendSms(jobExecutionContext.getJobDetail().getJobDataMap());
    }

    /**
     * 提醒短信的实现
     *
     * @param jobDataMap
     */
    private SendSmsResult sendSms(JobDataMap jobDataMap) {
        Integer messageType = jobDataMap.getInt(MESSAGE_TYPE); //短信文本类型
        String mobile = jobDataMap.getString(MOBILE);
        ArrayList<String> params = new ArrayList<>();
        initParams(params,messageType,jobDataMap);
        return SmsSendUtil.sendSms(mobile, messageType, params);
    }

    /**
     * 根据 messageType 从jobDataMap 初始化params
     *
     * @param params
     * @param messageType
     * @param jobDataMap
     */
    private void initParams(ArrayList<String> params, Integer messageType, JobDataMap jobDataMap) {
        if (messageType.equals(SmsSendUtil.ENROLL_SUCCESS) || messageType.equals(SmsSendUtil.DIAGNOSIS_REMIND)) {
            params.add(jobDataMap.getString(HOSPITAL_NAME));
            params.add(jobDataMap.getString(DIAGNOSIS_NAME));
            params.add(jobDataMap.getString(MEDICAL_CARD_NO));
            params.add(jobDataMap.getString(DOCTOR));
            params.add(jobDataMap.getString(DEPARTMENT));
            params.add(jobDataMap.getString(ENROLL_COST));
            params.add(jobDataMap.getString(DIAGNOSIS_TIME));
            params.add(jobDataMap.getString(DEPARTMENT_ADDRESS));
            params.add(jobDataMap.getString(DIAGNOSIS_ORDER));
        }
        if (messageType.equals(SmsSendUtil.CHECK_REMIND)) {
            params.add(jobDataMap.getString(HOSPITAL_NAME));
            params.add(jobDataMap.getString(DIAGNOSIS_NAME));
            params.add(jobDataMap.getString(MEDICAL_CARD_NO));
            params.add(jobDataMap.getString(DOCTOR));
            params.add(jobDataMap.getString(DEPARTMENT));
            params.add(jobDataMap.getString(DIAGNOSIS_TIME));
            params.add(jobDataMap.getString(DEPARTMENT_ADDRESS));
            params.add(jobDataMap.getString(DIAGNOSIS_ORDER));
        }
        if (messageType.equals(SmsSendUtil.EAT_DRUG_REMIND)) {
            // no params,do nothing
        }
    }

}
