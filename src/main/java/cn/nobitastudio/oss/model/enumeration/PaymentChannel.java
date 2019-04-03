package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 12:00
 * @description
 */
public enum PaymentChannel {
    @ApiModelProperty("微信支付")
    WECHAT_PAY,
    @ApiModelProperty("阿里支付")
    ALI_PAY,
    @ApiModelProperty("银联支付")
    UNION_PAY,
    @ApiModelProperty("QQ钱包")
    QQ_WALLET,
    @ApiModelProperty("医院现金支付")
    HOSPITAL_MONEY,
    @ApiModelProperty("医院诊疗卡支付")
    HOSPITAL_MEDICAL_CAR,
    @ApiModelProperty("未定义支付方式")
    UNDEFINED;
}
