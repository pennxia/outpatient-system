package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/11 11:53
 * @description
 */
public enum RemindType {
    @ApiModelProperty("挂号成功提醒")
    REGISTER_SUCCESS,
    @ApiModelProperty("就诊提醒")
    DIAGNOSIS_REMIND,
    @ApiModelProperty("取消预约提醒")
    CANCEL_REGISTER_REMIND,
    @ApiModelProperty("检查提醒")
    CHECK_REMIND,
    @ApiModelProperty("吃药提醒")
    EAT_DRUG_REMIND,
    @ApiModelProperty("活动提醒")
    ACTIVITY_REMIND,
    @ApiModelProperty("其他提醒")
    OTHER
}
