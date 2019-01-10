package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 12:06
 * @description 订单中各类子项的类型
 */
public enum ItemType {
    @ApiModelProperty("药品类型")
    DRUG,
    @ApiModelProperty("检查项类型")
    CHECK_ITEM,
    @ApiModelProperty("手术项类型")
    OPERATION_ITEM,
    @ApiModelProperty("挂号单类型")
    REGISTRATION_RECORD
}
