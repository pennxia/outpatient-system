package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/06 15:07
 * @description
 */
@Data
@Entity
@Table(name = "registration_record")
@Getter
@Setter
public class RegistrationRecord {

    @ApiModelProperty("挂号记录id")
    @Column(name = "id")
    @Id
    @Equal
    private Integer id;

    @ApiModelProperty("挂号者的id")
    @Column(name = "user_id")
    @Equal
    private Integer userId;

    @ApiModelProperty("所挂号的id")
    @Column(name = "visit_id")
    @Equal
    private Integer visitId;

    @ApiModelProperty("挂此号的诊疗卡id/卡号")
    @Column(name = "medical_card_id")
    @Equal
    private Integer medicalCardId;

    @ApiModelProperty("诊疗序号,即排在第几个")
    @Column(name = "diagnosis_no")
    @Equal
    private Integer diagnosisNo;

    @ApiModelProperty("创建日期")
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Equal
    private LocalDateTime createTime;
}
