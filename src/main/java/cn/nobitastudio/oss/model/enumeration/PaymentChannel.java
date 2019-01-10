package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 12:00
 * @description
 */
public enum PaymentChannel {

    @ApiModelProperty("支付宝支付")
    ALI_PAY,
    @ApiModelProperty("微信支付")
    WECHAT_PAY,
    @ApiModelProperty("银联支付")
    UNIONPAY_PAY
}
