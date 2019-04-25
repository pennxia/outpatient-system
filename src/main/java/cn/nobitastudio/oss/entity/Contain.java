package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.oss.model.enumeration.ItemType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 12:04
 * @description 订单 包含  收费项  包含关系
 */
@Data
@Entity
@Table(name = "contain")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contain implements Serializable {

    private static final long serialVersionUID = 7468056643932027289L;

    public Contain(String orderId, ItemType itemType, String itemId) {
        this.orderId = orderId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.amount = 1;
    }

    public Contain(String orderId, ItemType itemType, String itemId, Integer amount) {
        this.orderId = orderId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.amount = amount;
    }

    @ApiModelProperty("物理id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    private Integer id;

    @ApiModelProperty("订单id")
    @Column(name = "order_id")
    @Equal
    private String orderId;

    @ApiModelProperty("订单内包含的子项类型,枚举类型")
    @Column(name = "item_type")
    @Enumerated(EnumType.STRING)
    @Equal
    private ItemType itemType;

    @ApiModelProperty("订单内包含的子项的子项id")
    @Column(name = "item_id")
    @Equal
    private String itemId;

    @ApiModelProperty("购买数量")
    @Column(name = "amount")
    @Equal
    private Integer amount;
}
