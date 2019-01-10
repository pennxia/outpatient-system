package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @date 2019/01/03 14:36
 * @description 用户绑定诊疗卡诊疗卡关系.
 */
@Data
@Entity
@Table(name = "bind")
@Getter
@Setter
public class Bind implements Serializable {

    private static final long serialVersionUID = -9124180682454727169L;

    @ApiModelProperty("绑定物理id,无实力逻辑含义")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    private Integer id;

    @ApiModelProperty("绑定用户的手机号")
    @Column(name = "mobile")
    @Equal
    private String mobile;

    @ApiModelProperty("绑定的诊疗卡卡号")
    @Column(name = "medical_card_id")
    @Equal
    private String medicalCardId;

    @ApiModelProperty("绑定关系创建时间")
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Equal
    private LocalDateTime createTime;
}
