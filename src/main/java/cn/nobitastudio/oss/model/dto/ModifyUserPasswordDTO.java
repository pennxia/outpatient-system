package cn.nobitastudio.oss.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/13 23:13
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUserPasswordDTO implements Serializable {

    private static final long serialVersionUID = -5721770154919571626L;

    @ApiModelProperty("待修改用户id")
    private Integer userId;

    @ApiModelProperty("旧密码")
    private String oldPassword;

    @ApiModelProperty("新密码")
    private String newPassword;
}
