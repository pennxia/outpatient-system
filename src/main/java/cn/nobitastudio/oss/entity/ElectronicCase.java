package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.oss.model.enumeration.ElectronicCaseType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/24 16:43
 * @description 电子病历对应的实体
 */
@Data
@Entity
@Table(name = "electronic_case")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCase {

    public ElectronicCase(String orderId, String registrationRecordId, LocalDateTime diagnosisTime, String diagnosisDes, String diagnosisAdvise, String useDrugAdvise, String checkDes, String operationAdvise,String otherAdvise, ElectronicCaseType caseType) {
        this.orderId = orderId;
        this.registrationRecordId = registrationRecordId;
        this.diagnosisTime = diagnosisTime;
        this.diagnosisDes = diagnosisDes;
        this.diagnosisAdvise = diagnosisAdvise;
        this.useDrugAdvise = useDrugAdvise;
        this.checkDes = checkDes;
        this.operationAdvise = operationAdvise;
        this.otherAdvise = otherAdvise;
        this.caseType = caseType;
        switch (caseType) {
            case HOSPITALIZE:
                this.recoverTime = diagnosisTime.plusDays(14);
                break;
            case OUTPATIENT:
                this.recoverTime = diagnosisTime.plusHours(3);
                break;
            case EMERGENCY:
                this.recoverTime = diagnosisTime.plusMonths(2);
                break;
        }
    }

    @ApiModelProperty("绑定物理id,每一次就诊的病历详情")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    private Integer id;

    @ApiModelProperty("新生成订单的id")
    @Column(name = "order_id")
    @Equal
    private String orderId;

    @ApiModelProperty("本次就诊对应的挂号单id")
    @Column(name = "registration_record_id")
    @Equal
    private String registrationRecordId;

    @ApiModelProperty("用户到医院报到的时间")
    @Column(name = "diagnosis_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Equal
    private LocalDateTime diagnosisTime;

    @ApiModelProperty("当为住院类型的时候,表示出院时间也可以表示就诊结束时间")
    @Column(name = "recover_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Equal
    private LocalDateTime recoverTime;

    @ApiModelProperty("诊断描述")
    @Column(name = "diagnosis_des")
    private String diagnosisDes;

    @ApiModelProperty("诊断医嘱")
    @Column(name = "diagnosis_advise")
    private String diagnosisAdvise;

    @ApiModelProperty("用药医嘱")
    @Column(name = "use_drug_advise")
    private String useDrugAdvise;

    @ApiModelProperty("检查描述")
    @Column(name = "check_des")
    private String checkDes;

    @ApiModelProperty("术后医嘱")
    @Column(name = "operation_advise")
    private String operationAdvise;

    @ApiModelProperty("其他医嘱")
    @Column(name = "other_advise")
    private String otherAdvise;

    @ApiModelProperty("电子病历类型:门诊，住院，急诊")
    @Column(name = "case_type")
    @Equal
    @Enumerated(EnumType.STRING)
    private ElectronicCaseType caseType;
}
