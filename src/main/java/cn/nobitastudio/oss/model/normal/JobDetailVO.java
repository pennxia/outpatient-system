package cn.nobitastudio.oss.model.normal;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/07 10:38
 * @description
 */

@Getter
@Setter
public class JobDetailVO {
    private String mobile;
    private String hospitalName;
    private String diagnosisName;
    private String medicalCardId;
    private String doctorName;
    private String department;
    private String enrollCost;
    private String diagnosisTime;
    private String departmentAddress;
    private String diagnosisOrder;
}
