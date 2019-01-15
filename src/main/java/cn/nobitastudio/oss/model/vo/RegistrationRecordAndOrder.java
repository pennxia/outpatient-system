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
 * @date 2019/01/15 22:22
 * @description 挂号单以及对应的订单
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRecordAndOrder implements Serializable {

    private static final long serialVersionUID = 145816784813182584L;

    @ApiModelProperty("挂号单")
    private RegistrationRecord registrationRecord;
    @ApiModelProperty("挂号单对应的订单")
    private OSSOrder ossOrder;
}
