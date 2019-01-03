package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import cn.nobitastudio.oss.vo.Enum.Sex;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
public class MedicalCard implements Serializable {

    private static final long serialVersionUID = -914979755295639051L;

    @ApiModelProperty("诊疗卡物理id,没有逻辑含义")
    @Column(name = "id")
    @Id
    @Equal
    private Integer id;

    @ApiModelProperty("诊疗卡号")
    @Column(name = "medical_card_no")
    @Equal
    private String medicalCardNo;

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

}
