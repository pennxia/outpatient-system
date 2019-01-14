package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/12 15:31
 * @description 生成quartz调度计划时  triggerKey 和 jobKey 的类型.
 */
public enum QuartzPlanType {

    @ApiModelProperty("就诊提醒类型")
    DIAGNOSIS_REMIND,
    @ApiModelProperty("检测订单状态类型")
    CHECK_ORDER_STATE,

}
