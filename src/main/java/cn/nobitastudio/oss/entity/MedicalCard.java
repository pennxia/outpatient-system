package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import cn.nobitastudio.oss.model.enumeration.Channel;
import cn.nobitastudio.oss.model.enumeration.ItemType;
import cn.nobitastudio.oss.model.enumeration.Sex;
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
 * @date 2019/01/02 16:08
 * @description 诊疗卡实例
 */
@Data
@Entity
@Table(name = "medical_card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalCard implements Serializable {

    private static final long serialVersionUID = -914979755295639051L;

    @ApiModelProperty("诊疗卡id,也是卡号")
    @Column(name = "id")
    @Id
    @Equal
    private String id;

    @ApiModelProperty("持卡者姓名")
    @Column(name = "owner_name")
    @Like
    private String ownerName;

    @ApiModelProperty("持卡者身份证号")
    @Column(name = "owner_id_card")
    @Equal
    private String ownerIdCard;

    @ApiModelProperty("持卡者性别")
    @Column(name = "owner_sex")
    @Enumerated(EnumType.STRING)
    @Equal
    private Sex ownerSex;

    @ApiModelProperty("持卡者住址")
    @Column(name = "owner_address")
    @Like
    private String ownerAddress;

    @ApiModelProperty("持卡者联系方式")
    @Column(name = "owner_mobile")
    @Equal
    private String ownerMobile;

    @ApiModelProperty("创建日期")
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Equal
    private LocalDateTime createTime;

    /**
     * 更新诊疗卡信息
     * @param medicalCard
     */
    public MedicalCard update(MedicalCard medicalCard) {
        if (medicalCard.getOwnerName() != null) {
            this.ownerName = medicalCard.getOwnerName();
        }
        if (medicalCard.getOwnerIdCard() != null) {
            this.ownerIdCard = medicalCard.getOwnerIdCard();
        }
        if (medicalCard.getOwnerSex() != null) {
            this.ownerSex = medicalCard.getOwnerSex();
        }
        if (medicalCard.getOwnerAddress() != null) {
            this.ownerAddress = medicalCard.getOwnerAddress();
        }
        if (medicalCard.getOwnerMobile() != null) {
            this.ownerMobile = medicalCard.getOwnerMobile();
        }
        return this;
    }

    /**
     * 创建诊疗卡时,对这诊疗卡进行初始化
     */
    public void init() {
        this.setId(SnowFlakeUtil.getUniqueId(ItemType.MEDICAL_CARD.ordinal() + 1 + Channel.values().length).toString());
        this.createTime = LocalDateTime.now();
    }

}
