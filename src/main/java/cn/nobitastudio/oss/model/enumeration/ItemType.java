package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 12:06
 * @description 业务领域(订单中各类子项的类型) 对应于订单名称,订单id等生成
 */
public enum ItemType {
    @ApiModelProperty("挂号")
    REGISTER,
    @ApiModelProperty("邮寄")
    EXPRESS,
    @ApiModelProperty("药品")
    DRUG,
    @ApiModelProperty("检查订")
    CHECK,
    @ApiModelProperty("手术")
    OPERATION,
    @ApiModelProperty("住院")
    HOSPITALIZE,
    @ApiModelProperty("诊疗卡类型")
    MEDICAL_CARD;
}
