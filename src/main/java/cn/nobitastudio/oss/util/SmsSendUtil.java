package cn.nobitastudio.oss.util;

import cn.nobitastudio.oss.vo.SendSmsResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
public class SmsSendUtil {
    static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    private static final int appid = 1400107331;

    private static final String appkey = "0e0b67106450077ff4a8cb3b0cf8a876";

    private static final String smsSign = "大雄咩咩";

    //0.注册账号 1.找回密码 2.修改密码 3.办理诊疗卡 4.绑定诊疗卡 5.挂号成功 6.取消预约挂号 7.就诊提醒 8.检查提醒 9.吃药提醒
    private static final int[] templateId = {147950, 147954, 147957, 156224, 156422, 260272, 260277, 260291, 260407, 260316};

    public static final int REGISTER = 0;// 注册账号
    public static final int RETRIEVE_PASSWORD = 1;//找回密码
    public static final int UPDATE_PASSWORD = 2;//修改密码
    public static final int CREATE_MEDICAL_CARD = 3;//办理诊疗卡
    public static final int BIND_MEDICAL_CARD = 4;//绑定诊疗卡
    public static final int ENROLL_SUCCESS = 5;//挂号成功
    public static final int CANCEL_ENROLL = 6;//取消预约挂号
    public static final int DIAGNOSIS_REMIND = 7;//就诊提醒
    public static final int CHECK_REMIND = 8;//检查提醒
    public static final int EAT_DRUG_REMIND = 9;//吃药提醒
    /**
     * 发送短信给指定用户
     *
     * @param account     接收短信的用户
     * @param messageType 短信文本类型
     * @param params      短信中的参数
     */
    public static SendSmsResult sendSms(String account, int messageType, ArrayList<String> params) {
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        SmsSingleSenderResult result;  // 签名参数未提供或者为空时，会使用默认签名发送短信
        SendSmsResult sendSmsResult = new SendSmsResult();
        try {
            result = ssender.sendWithParam("86", account, templateId[messageType], params, smsSign, "", "");
            if (result.result == 0) {
                //send success
                sendSmsResult.setResult(true);
                logger.info("短信发送成功,接收者:{},短信类型：{},发送时间：{}",account,messageType,LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT)));
            } else {
                //send fail
                sendSmsResult.setResult(false);
                logger.info("短信发送成功,接收者:{},短信类型：{},发送时间：{}",account,messageType,LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT)));
            }
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sendSmsResult;
    }

    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<String> params = new ArrayList<>();
        params.add("11111");
        new SmsSendUtil().sendSms("15709932234", 1, params);
    }

}
