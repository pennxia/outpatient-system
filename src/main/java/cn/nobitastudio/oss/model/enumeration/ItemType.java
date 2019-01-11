package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 12:06
 * @description 业务领域(订单中各类子项的类型) 对应于订单名称,订单id等生成
 */
public enum ItemType {
    @ApiModelProperty("药品类型")
    DRUG,
    @ApiModelProperty("检查项类型")
    CHECK_ITEM,
    @ApiModelProperty("手术项类型")
    OPERATION_ITEM,
    @ApiModelProperty("挂号单类型")
    REGISTRATION_RECORD,
    @ApiModelProperty("诊疗卡类型")
    MEDICAL_CARD
}
