package cn.nobitastudio.oss.vo.enumeration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/09 09:36
 * @description
 */
public enum RoleName {

    @ApiModelProperty("管理员")
    ADMIN,
    @ApiModelProperty("普通用户")
    USER,
    @ApiModelProperty("数据库管理员")
    DB_ADMIN
}
