package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 13:43
 * @description
 */
public enum DoctorLevel {
    @ApiModelProperty("一级专家")
    ONE,
    @ApiModelProperty("二级专家")
    TWO,
    @ApiModelProperty("三级专家")
    THREE,
    @ApiModelProperty("教授")
    PROFESSOR,
    @ApiModelProperty("副教授")
    ASSOCIATE_PROFESSOR,
    @ApiModelProperty("主治医师")
    ATTENDING_PHYSICIAN
}
