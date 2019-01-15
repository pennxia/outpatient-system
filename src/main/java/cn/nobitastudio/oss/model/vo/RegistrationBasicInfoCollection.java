package cn.nobitastudio.oss.model.vo;

import cn.nobitastudio.oss.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/15 22:39
 * @description 挂号单基础信息集合,其中包含 department ,doctor.medicalCard,visit.registrationRecord.diagnosisRoom ,ossOrder用于查询单次挂号单详情时进行调用
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationBasicInfoCollection {

    @ApiModelProperty("挂号单")
    private RegistrationRecord registrationRecord;
    @ApiModelProperty("挂号单中对应的科室")
    private Department department;
    @ApiModelProperty("挂号单坐诊的医生")
    private Doctor doctor;
    @ApiModelProperty("挂号单对应的诊疗卡")
    private MedicalCard medicalCard;
    @ApiModelProperty("挂号单对应的挂号资源")
    private Visit visit;
    @ApiModelProperty("挂号单对应的诊疗室")
    private DiagnosisRoom diagnosisRoom;
    @ApiModelProperty("挂号单对应的订单")
    private OSSOrder ossOrder;
}
