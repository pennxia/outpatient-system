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
import java.util.Date;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 11:26
 * @description 挂号的号源
 */
@Data
@Entity
@Table(name = "visit")
@Getter
@Setter
public class Visit implements Serializable {

    private static final long serialVersionUID = -8300723782462137747L;

    @ApiModelProperty("号源id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    private Integer id;

    @ApiModelProperty("坐诊医生id")
    @Column(name = "doctor_id")
    @Equal
    private Integer doctorId;

    @ApiModelProperty("单个号源单价")
    @Column(name = "cost")
    @Equal
    private Double cost;

    @ApiModelProperty("诊断时间")
    @Column(name = "diagnosis_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Equal
    private LocalDateTime diagnosisTime;

    @ApiModelProperty("总数")
    @Column(name = "amount")
    @Equal
    private Integer amount;

    @ApiModelProperty("剩余数量")
    @Column(name = "left_amount")
    @Equal
    private Integer leftAmount;
}
