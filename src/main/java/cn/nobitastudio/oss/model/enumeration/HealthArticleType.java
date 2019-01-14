package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 17:22
 * @description
 */
public enum HealthArticleType {

    @ApiModelProperty("健康头条")
    HEALTH_HEADLINE,
    @ApiModelProperty("名医讲堂")
    PHYSICIAN_LECTURE,
    @ApiModelProperty("医院活动")
    HOSPITAL_ACTIVITY
}
