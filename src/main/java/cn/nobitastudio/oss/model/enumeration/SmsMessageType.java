package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/12 12:35
 * @description 短信内容认证
 */
public enum SmsMessageType {

    @ApiModelProperty("注册账号")
    ENROLL,
    @ApiModelProperty("找回密码")
    RETRIEVE_PASSWORD,
    @ApiModelProperty("修改密码")
    UPDATE_PASSWORD,
    @ApiModelProperty("办理诊疗卡")
    CREATE_MEDICAL_CARD,
    @ApiModelProperty("绑定诊疗卡")
    BIND_MEDICAL_CARD,
    @ApiModelProperty("挂号支付成功")
    REGISTER_SUCCESS_HAVE_PAY,
    @ApiModelProperty("取消预约挂号")
    CANCEL_REGISTER,
    @ApiModelProperty("就诊提醒")
    DIAGNOSIS_REMIND,
    @ApiModelProperty("检查提醒")
    CHECK_REMIND,
    @ApiModelProperty("吃药提醒")
    EAT_DRUG_REMIND,
    @ApiModelProperty("挂号成功等待支付")
    REGISTER_SUCCESS_WAITING_PAY,
}
