package cn.nobitastudio.oss.model.vo;

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
public class UserCreateVO {

    @ApiModelProperty("用户手机号")
    private String mobile;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户身份证号码")
    private String idCard;

}
