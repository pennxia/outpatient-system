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
 * @date 2019/01/14 15:26
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmValidateCodeDTO implements Serializable {

    private static final long serialVersionUID = 2242134464115320507L;

    @ApiModelProperty("请求的手机号")
    private String mobile;
    @ApiModelProperty("待验证的验证码")
    private String code;
}
