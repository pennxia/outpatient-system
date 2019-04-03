package cn.nobitastudio.oss.model.dto;

import cn.nobitastudio.oss.model.enumeration.PaymentChannel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/13 00:45
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRegisterDTO implements Serializable {

    private static final long serialVersionUID = -6966923386468771663L;

    @ApiModelProperty("挂号单单号/id")
    private String registrationRecordId;

    @ApiModelProperty("支付渠道")
    @Enumerated(EnumType.STRING)
    private PaymentChannel paymentChannel;
}
