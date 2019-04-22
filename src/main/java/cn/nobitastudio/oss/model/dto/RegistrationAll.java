package cn.nobitastudio.oss.model.dto;

import cn.nobitastudio.oss.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/22 19:27
 * @description 挂号单包含的全部对象
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationAll {

    @ApiModelProperty("挂号单实体")
    private RegistrationRecord registrationRecord;

    @ApiModelProperty("科室")
    private Department department;

    @ApiModelProperty("医生")
    private Doctor doctor;

    @ApiModelProperty("挂号者实体")
    private User user;

    @ApiModelProperty("号源实体")
    private Visit visit;

    @ApiModelProperty("诊疗卡实体")
    private MedicalCard medicalCard;

    @ApiModelProperty("订单实体")
    private OSSOrder ossOrder;

    @ApiModelProperty("诊室")
    private DiagnosisRoom diagnosisRoom;
}
