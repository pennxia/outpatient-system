package cn.nobitastudio.oss.helper;

import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.oss.entity.*;
import cn.nobitastudio.oss.model.dto.ValidateCode;
import cn.nobitastudio.oss.model.enumeration.SmsMessageType;
import cn.nobitastudio.oss.model.vo.SmsSendResult;
import cn.nobitastudio.oss.util.DateUtil;
import com.github.qcloudsms.*;
import com.github.qcloudsms.httpclient.HTTPException;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class SmsHelper {
    static Logger logger = LoggerFactory.getLogger(SmsHelper.class);
    private static final int APP_ID = 1400107331;

    private static final String APP_KEY = "0e0b67106450077ff4a8cb3b0cf8a876";

    private static final String SMS_SIGN = "大雄咩咩";

    //0.注册账号 1.找回密码 2.修改密码 3.办理诊疗卡 4.绑定诊疗卡 5.挂号支付成功 6.取消预约挂号 7.就诊提醒 8.检查提醒 9.吃药提醒 10.挂号成功等待支付
    private static final int[] TEMPLATE_ID = {147950, 147954, 147957, 156224, 156422, 265364, 265454, 260291, 264717, 260316, 265368};

    public static final String MESSAGE_TYPE = "messageType";
    public static final int ENROLL = 0;// 注册账号
    public static final int RETRIEVE_PASSWORD = 1;//找回密码
    public static final int UPDATE_PASSWORD = 2;//修改密码
    public static final int CREATE_MEDICAL_CARD = 3;//办理诊疗卡
    public static final int BIND_MEDICAL_CARD = 4;//绑定诊疗卡
    public static final int REGISTER_SUCCESS_HAVE_PAY = 5;//挂号支付成功
    public static final int CANCEL_REGISTER = 6;//取消预约挂号
    public static final int DIAGNOSIS_REMIND = 7;//就诊提醒
    public static final int CHECK_REMIND = 8;//检查提醒
    public static final int EAT_DRUG_REMIND = 9;//吃药提醒
    public static final int REGISTER_SUCCESS_WAITING_PAY = 10; // 挂号成功等待支付

    public static final String MOBILE = "mobile";
    public static final String HOSPITAL_NAME = "hospitalName";
    public static final String MEDICAL_CARD_ID = "medicalCardId";
    public static final String DOCTOR_NAME = "doctor";
    public static final String DEPARTMENT_NAME = "medicalCardName";
    public static final String REGISTRATON_RECORD_ID = "registrationRecordId";
    public static final String REGISTER_COST = "registerCost";
    public static final String DIAGNOSIS_TIME = "diagnosisTime";
    public static final String DIAGNOSIS_ROOM = "departmentAddress";
    public static final String DIAGNOSIS_NO = "diagnosisNo";
    public static final String VALIDATE_CODE = "validateCode";
    public static final String MEDICAL_CARD_OWNER = "medicalCardOwner";
    public static final String ORDER_ID = "order_id";
    public static final String CHECK_TIME = "checkTime";
    public static final String CHECK_ROOM_ADDRESS = "checkRoomAddress";
    public static final String CHECK_NO = "checkNo";

    public static final String DEFAULT_HOSPITAL_NAME = "石河子大学医学院第一附属医院";

    private static SmsSingleSender smsSender = new SmsSingleSender(APP_ID, APP_KEY);

    /**
     * 发送短信给指定用户
     *
     * @param mobile         接收短信的用户
     * @param smsMessageType 短信文本类型
     * @param params         短信中的参数
     */
    private SmsSendResult sendSmsRightNow(String mobile, SmsMessageType smsMessageType, ArrayList<String> params) {
        SmsSingleSenderResult result;  // 签名参数未提供或者为空时，会使用默认签名发送短信
        SmsSendResult smsSendResult = new SmsSendResult();
        try {
            result = smsSender.sendWithParam("86", mobile, TEMPLATE_ID[smsMessageType.ordinal()], params, SMS_SIGN, "", "");
            if (result.result == 0) {
                //send success
                smsSendResult.setResult(true);
                logger.info("短信发送成功,接收者:{},短信类型：{},发送时间：{}", mobile, smsMessageType.name(), DateUtil.formatLocalDateTimeToStardardString(LocalDateTime.now()));
            } else {
                //send fail
                smsSendResult.setResult(false);
                logger.info("短信发送成功,接收者:{},短信类型：{},发送时间：{}", mobile, smsMessageType.name(), DateUtil.formatLocalDateTimeToStardardString(LocalDateTime.now()));
            }
        } catch (HTTPException | IOException e) {
            logger.info("腾讯云SMS SDK出错");
            e.printStackTrace();
        }
        return smsSendResult;
    }

    // 待修改todo smsMessageType.ordinal() -> smsMessageType
    public SmsSendResult sendSms(Map<String, String> paramsMap) {
        ArrayList<String> params = new ArrayList<>();  // 发送的实际参数
        String mobile = paramsMap.get(MOBILE);      // 接收者的手机号
        SmsMessageType smsMessageType = SmsMessageType.valueOf(paramsMap.get(MESSAGE_TYPE));  // 发送的短信类型
        switch (smsMessageType.ordinal()) {
            case ENROLL:
            case RETRIEVE_PASSWORD:
            case UPDATE_PASSWORD:
            case CREATE_MEDICAL_CARD:
                params.add(paramsMap.get(VALIDATE_CODE));  // 只需获得验证码
                break;
            case BIND_MEDICAL_CARD:
                params.add(paramsMap.get(VALIDATE_CODE));
                params.add(paramsMap.get(MEDICAL_CARD_ID));
                params.add(paramsMap.get(MEDICAL_CARD_OWNER));
                break;
            case CANCEL_REGISTER:
                params.add(paramsMap.get(REGISTRATON_RECORD_ID));
                params.add(paramsMap.get(ORDER_ID));
                params.add(paramsMap.getOrDefault(HOSPITAL_NAME, DEFAULT_HOSPITAL_NAME));
                break;
            case REGISTER_SUCCESS_WAITING_PAY:
            case REGISTER_SUCCESS_HAVE_PAY:
            case DIAGNOSIS_REMIND:
                params.add(paramsMap.getOrDefault(HOSPITAL_NAME, DEFAULT_HOSPITAL_NAME));
                params.add(paramsMap.get(MEDICAL_CARD_OWNER));
                params.add(paramsMap.get(MEDICAL_CARD_ID));
                params.add(paramsMap.get(DOCTOR_NAME));
                params.add(paramsMap.get(DEPARTMENT_NAME));
                params.add(paramsMap.get(REGISTER_COST));
                params.add(paramsMap.get(DIAGNOSIS_TIME));
                params.add(paramsMap.get(DIAGNOSIS_ROOM));
                params.add(paramsMap.get(DIAGNOSIS_NO));
                break;
            case CHECK_REMIND:
                params.add(paramsMap.getOrDefault(HOSPITAL_NAME, DEFAULT_HOSPITAL_NAME));
                params.add(paramsMap.get(MEDICAL_CARD_OWNER));
                params.add(paramsMap.get(MEDICAL_CARD_ID));
                params.add(paramsMap.get(DEPARTMENT_NAME));
                params.add(paramsMap.get(CHECK_TIME));
                params.add(paramsMap.get(CHECK_ROOM_ADDRESS));
                params.add(paramsMap.get(CHECK_NO));
                break;
            case EAT_DRUG_REMIND:
                params.add(paramsMap.getOrDefault(HOSPITAL_NAME, DEFAULT_HOSPITAL_NAME));
                break;
            default:
                throw new AppException("未支持发送该类型的短信模板");
        }
        return sendSmsRightNow(mobile, smsMessageType, params);
    }

    /**
     * 初始化挂号成功以及就诊未来提醒的短信参数
     *
     * @param user
     * @param medicalCard
     * @param doctor
     * @param department
     * @param visit
     * @param diagnosisNo
     * @return
     */
    // todo 接口待修改.不需要传入messageType
    public Map<String, String> initRegisterSuccessOrDiagnosisRemindSms(User user, MedicalCard medicalCard, Doctor doctor, Department department, Visit visit, DiagnosisRoom diagnosisRoom, Integer diagnosisNo, SmsMessageType smsMessageType) {
        Map<String, String> params = new HashMap<>();
        if (smsMessageType.equals(SmsMessageType.REGISTER_SUCCESS_HAVE_PAY) || smsMessageType.equals(SmsMessageType.REGISTER_SUCCESS_WAITING_PAY)) {
            params.put(MOBILE, user.getMobile());  //  给挂号者发送挂号成功短信
        } else if (smsMessageType.equals(SmsMessageType.DIAGNOSIS_REMIND)) {
            params.put(MOBILE, medicalCard.getOwnerMobile());   // 给就诊者发送及时就诊短信
        } else {
            throw new AppException("不支持此类短信参数的初始化");
        }
        params.put(MESSAGE_TYPE, smsMessageType.name());
        params.put(MEDICAL_CARD_OWNER, medicalCard.getOwnerName());
        params.put(MEDICAL_CARD_ID, medicalCard.getId());
        params.put(DOCTOR_NAME, doctor.getName());
        params.put(DEPARTMENT_NAME, department.getName());
        params.put(REGISTER_COST, visit.getCost() + "元");
        params.put(DIAGNOSIS_TIME, DateUtil.formatLocalDateTimeToStardardString(visit.getDiagnosisTime()));
        params.put(DIAGNOSIS_ROOM, diagnosisRoom.getAddress() + "(" + diagnosisRoom.getArea().name() + "区" + diagnosisRoom.getFloor() + "层," +
                "第" + diagnosisRoom.getLocation() + "诊室)");
        params.put(DIAGNOSIS_NO, diagnosisNo + "号");
        return params;
    }

    public Map<String, String> initCancelRegisterSms(User user, RegistrationRecord registrationRecord, OSSOrder ossOrder) {
        Map<String, String> params = new HashMap<>();
        params.put(MOBILE, user.getMobile());
        params.put(MESSAGE_TYPE, SmsMessageType.CANCEL_REGISTER.name());
        params.put(REGISTRATON_RECORD_ID, registrationRecord.getId());
        params.put(ORDER_ID, ossOrder.getId());
        return params;
    }

    public Map<String, String> initRequestValidateCodeSms(String mobile, SmsMessageType smsMessageType, ValidateCode validateCode) {
        Map<String, String> params = new HashMap<>();
        params.put(MOBILE, mobile);
        params.put(MESSAGE_TYPE, smsMessageType.name());
        params.put(VALIDATE_CODE, validateCode.getCode());
        return params;
    }

    public Map<String,String> initBindMedicalCardSms(String mobile,SmsMessageType smsMessageType,ValidateCode validateCode,MedicalCard medicalCard) {
        Map<String, String> params = new HashMap<>();
        params.put(MOBILE, mobile);
        params.put(MESSAGE_TYPE, smsMessageType.name());
        params.put(VALIDATE_CODE, validateCode.getCode());
        params.put(MEDICAL_CARD_ID,medicalCard.getId());
        params.put(MEDICAL_CARD_OWNER,medicalCard.getOwnerName());
        return params;
    }


    public Map<String, String> castJobDataMapToMap(JobDataMap jobDataMap) {
        Map<String, String> params = new HashMap<>();
        jobDataMap.forEach((key, value) -> params.put(key, (String) value));
        return params;
    }


    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) throws HTTPException, IOException {
//        SmsVoiceVerifyCodeSender vvcsender = new SmsVoiceVerifyCodeSender(APP_ID, APP_KEY);
//        SmsVoiceVerifyCodeSenderResult result = vvcsender.send("86", "15709932234",
//                "5678", 2, "");

//        SmsVoicePromptSender vpsender = new SmsVoicePromptSender(APP_ID, APP_KEY);
//        SmsVoicePromptSenderResult result2 = vpsender.send("86", "15709932234",
//                2, 2, "5678", "");
//        System.out.println(result2);
    }
}
