package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@NoArgsConstructor
@AllArgsConstructor
public class Bind implements Serializable {

    private static final long serialVersionUID = -9124180682454727169L;

    public Bind(Integer userId, String medicalCardId) {
        this.userId = userId;
        this.medicalCardId = medicalCardId;
        createTime = LocalDateTime.now();
    }

    @ApiModelProperty("绑定物理id,无实力逻辑含义")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    private Integer id;

    @ApiModelProperty("绑定用户的手机号")
    @Column(name = "user_id")
    @Equal
    private Integer userId;

    @ApiModelProperty("绑定的诊疗卡卡号")
    @Column(name = "medical_card_id")
    @Equal
    private String medicalCardId;

    @ApiModelProperty("绑定关系创建时间")
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Equal
    private LocalDateTime createTime;

    /**
     * 创建时进行初始化
     */
    public void init(){
        this.createTime = LocalDateTime.now();
    }
}
