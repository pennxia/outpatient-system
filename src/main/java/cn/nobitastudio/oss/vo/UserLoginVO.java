package cn.nobitastudio.oss.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginVO {

    @ApiModelProperty("用户登录所用的手机号")
    private String phone;

    @ApiModelProperty("用户登录时所用的密码")
    private String password;
}
