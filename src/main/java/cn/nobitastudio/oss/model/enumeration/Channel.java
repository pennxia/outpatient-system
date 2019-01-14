package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 17:41
 * @description 用户的所产生信息的渠道,用于挂号单,订单号生成
 */
public enum Channel {
    @ApiModelProperty("通过Android客户端进行的挂号,对应于产生的挂号单单号为:1")
    OSS_ANDROID_APP,
    @ApiModelProperty("通过IOS客户端进行的挂号")
    OSS_IOS_APP,
    @ApiModelProperty("通过WEB网页客户端进行的挂号")
    OSS_WEB_APP,
    @ApiModelProperty("通过在医院挂号处进行的挂号")
    OSS_HOSPITAL_LOCAL,
    @ApiModelProperty("其他挂号方式")
    OSS_OTHER
}
