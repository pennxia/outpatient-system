package cn.nobitastudio.oss.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 12:20
 * @description 用户挂号的数据传输对象
 */
@Getter
@Setter
public class RegisterDTO implements Serializable {

    private static final long serialVersionUID = -4329830827839495650L;

    @ApiModelProperty("挂号用户的id")
    private Integer userId;
    @ApiModelProperty("该次挂号所享受服务的诊疗卡id")
    private String medicalCardId;
    @ApiModelProperty("号源id")
    private Integer visitId;
}
