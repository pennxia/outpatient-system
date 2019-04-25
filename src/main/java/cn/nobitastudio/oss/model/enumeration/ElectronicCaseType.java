package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/24 16:52
 * @description 电子病历类型 门诊记录  住院记录  急诊记录
 */
public enum ElectronicCaseType {
    @ApiModelProperty("门诊记录")
    OUTPATIENT,
    @ApiModelProperty("住院记录")
    HOSPITALIZE,
    @ApiModelProperty("急诊记录")
    EMERGENCY;

    public static ElectronicCaseType getFromPosition(int pos) {
        switch (pos) {
            case 0:
                return OUTPATIENT;
            case 1:
                return HOSPITALIZE;
            case 2:
                return EMERGENCY;
            default:
                return OUTPATIENT;
        }
    }
}
