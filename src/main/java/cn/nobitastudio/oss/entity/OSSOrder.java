package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import cn.nobitastudio.oss.model.enumeration.OrderState;
import cn.nobitastudio.oss.model.enumeration.PaymentChannel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:14
 * @description 订单实例
 * 生成策略：下单渠道1位 + 业务类型1位 + 时间信息4位 + 下单时间的Unix时间戳后8位（加上随机码随机后的数字）+ 用户user id后4位
 */
@Data
@Entity
@Table(name = "oss_order")
@Getter
@Setter
public class OSSOrder implements Serializable {

    private static final long serialVersionUID = -8428754043223464384L;

    @ApiModelProperty("订单id")
    @Column(name = "id")
    @Id
    @Equal
    private String id;

    @ApiModelProperty("订单的产生用户id")
    @Column(name = "user_id")
    @Equal
    private Integer userId;

    @ApiModelProperty("该订单服务的真正享受者诊疗卡号")
    @Column(name = "medical_card_id")
    @Equal
    private String medicalCardId;

    @ApiModelProperty("订单名称")
    @Column(name = "name")
    @Like
    private String name;

    @ApiModelProperty("支付状态,枚举值")
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    @Equal
    private OrderState state;

    @ApiModelProperty("订单创建时间")
    @Column(name = "create_time")
    @Equal
    private LocalDateTime createTime;

    @ApiModelProperty("支付渠道,枚举值")
    @Column(name = "payment_channel")
    @Enumerated(EnumType.STRING)
    @Equal
    private PaymentChannel paymentChannel;

    @ApiModelProperty("支付流水号")
    @Column(name = "serial_number")
    @Equal
    private String serialNumber;

    @ApiModelProperty("支付时间")
    @Column(name = "pay_time")
    @Equal
    private LocalDateTime payTime;

    @ApiModelProperty("取消支付时间,取消订单时间")
    @Column(name = "cancel_time")
    @Equal
    private String cancelTime;
}
