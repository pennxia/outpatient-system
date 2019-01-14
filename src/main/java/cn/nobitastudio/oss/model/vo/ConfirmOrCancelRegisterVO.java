package cn.nobitastudio.oss.model.vo;

import cn.nobitastudio.oss.entity.OSSOrder;
import cn.nobitastudio.oss.entity.RegistrationRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/13 00:43
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmOrCancelRegisterVO implements Serializable {

    private static final long serialVersionUID = 1046437035255760058L;

    @ApiModelProperty("订单信息")
    private OSSOrder ossOrder;
    @ApiModelProperty("挂号单信息")
    private RegistrationRecord registrationRecord;
}
