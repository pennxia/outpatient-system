package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 11:46
 * @description 订单状态
 */
public enum OrderState {

    @ApiModelProperty("已经支付")
    HAVE_PAY,
    @ApiModelProperty("等待支付")
    WAITING_PAY,
    @ApiModelProperty("取消支付")
    CANCEL_PAY,
    @ApiModelProperty("自动取消")
    AUTO_CANCEL_PAY
}
