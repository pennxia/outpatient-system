package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.oss.model.enumeration.Channel;
import cn.nobitastudio.oss.model.enumeration.ItemType;
import cn.nobitastudio.oss.model.enumeration.OrderState;
import cn.nobitastudio.oss.model.enumeration.PaymentChannel;
import cn.nobitastudio.oss.util.SnowFlakeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:14
 * @description 订单实例
 * id生成策略：通过雪花算法生成唯一id,配合订单的类型所绑定的作为 数据中心
 * 流水号生成策略 :
 */
@Data
@Entity
@Table(name = "oss_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OSSOrder implements Serializable {

    private static final long serialVersionUID = -8428754043223464384L;

    public OSSOrder(ItemType itemType, Integer userId, String medicalCardId,Double cost) {
        this.id =  SnowFlakeUtil.getUniqueId(itemType.ordinal() + 1 + Channel.values().length).toString(); // 生成挂号单号时,dataCenterId默认从id开始;
        this.userId = userId;
        this.medicalCardId = medicalCardId;
        this.itemType = itemType;
        this.state = OrderState.WAITING_PAY;
        this.createTime = LocalDateTime.now();
        this.cost = cost;
    }

    // 用于生成电子病历时使用
    public OSSOrder(String medicalCardId,double cost) {
        this.id =  SnowFlakeUtil.getUniqueId(ItemType.ELECTRONIC_CASE.ordinal() + 1 + Channel.values().length).toString(); // 生成挂号单号时,dataCenterId默认从id开始;
        this.userId = null;  // 没有 APP支付者
        this.medicalCardId = medicalCardId;
        this.itemType = ItemType.ELECTRONIC_CASE;
        this.state = OrderState.HAVE_PAY; // 生成时即完成支付了
        this.createTime = LocalDateTime.now();
        this.paymentChannel = PaymentChannel.HOSPITAL_MEDICAL_CAR; //  在医院使用诊疗卡余额支付
        this.payTime = createTime.plusHours(1).plusMinutes(10).plusSeconds(20); // 默认创建时间的1小时10分钟20秒后
        this.cost = cost;
    }

    @ApiModelProperty("订单id,流水号")
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

    @ApiModelProperty("订单内包含的子项类型,枚举类型")
    @Column(name = "item_type")
    @Enumerated(EnumType.STRING)
    @Equal
    private ItemType itemType;

    @ApiModelProperty("支付状态,枚举值")
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    @Equal
    private OrderState state;

    @ApiModelProperty("订单创建时间")
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Equal
    private LocalDateTime createTime;

    @ApiModelProperty("支付渠道,枚举值")
    @Column(name = "payment_channel")
    @Enumerated(EnumType.STRING)
    @Equal
    private PaymentChannel paymentChannel;

    @ApiModelProperty("支付时间")
    @Column(name = "pay_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Equal
    private LocalDateTime payTime;

    @ApiModelProperty("取消支付时间,取消订单时间")
    @Column(name = "cancel_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Equal
    private LocalDateTime cancelTime;

    @ApiModelProperty("订单花费")
    @Column(name = "cost")
    private Double cost;
}
