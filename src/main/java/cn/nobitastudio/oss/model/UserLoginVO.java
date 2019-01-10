package cn.nobitastudio.oss.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/10 17:34
 * @description
 */
@Getter
@Setter
public class UserLoginVO {

    @ApiModelProperty("用户登录所用的手机号")
    private String phone;

    @ApiModelProperty("用户登录时所用的密码")
    private String password;
}
