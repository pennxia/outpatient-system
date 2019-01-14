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
 * @date 2019/01/13 23:24
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUserMobileDTO implements Serializable {

    private static final long serialVersionUID = 8839233109963236524L;

    @ApiModelProperty("需要更改绑定手机号的用户id")
    private Integer userId;

    @ApiModelProperty("新的绑定手机号")
    private String newMobile;
}
