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
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/06 15:07
 * @description
 * 生成策略：下单渠道1位 + 业务类型1位 + 时间信息4位 + 下单时间的Unix时间戳后8位（加上随机码随机后的数字）+ 用户user id后4位
 */
@Data
@Entity
@Table(name = "registration_record")
@Getter
@Setter
public class RegistrationRecord implements Serializable {

    private static final long serialVersionUID = -7653921447104816273L;

    @ApiModelProperty("挂号记录id,挂号单号")
    @Column(name = "id")
    @Id
    @Equal
    private String id;

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
    private String medicalCardId;

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
