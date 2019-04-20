package cn.nobitastudio.oss.model.vo;

import cn.nobitastudio.oss.entity.Department;
import cn.nobitastudio.oss.entity.Doctor;
import cn.nobitastudio.oss.model.enumeration.Area;
import cn.nobitastudio.oss.model.enumeration.DoctorLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/15 22:57
 * @description 医生以及对应的科室的封装对象
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAndDepartment implements Serializable {

    private static final long serialVersionUID = 559299959620878568L;

    /**
     * 对连表结果强转时进行调用
     */
    public DoctorAndDepartment(Integer doctorId, String doctorName, String doctorSpecialty, String doctorSubMajor, String doctorIntroduction,  // docor
                               String doctorLevel, String iconUrl, Integer doctorDepartmentId,
                               Integer departmentId, String departmentName, String departmentAddress, Integer departmentLocation,  // department
                               Integer departmentFloor, String area, String departmentIntroduction) {
        this.doctor = new Doctor(doctorId, doctorName, doctorSpecialty, doctorSubMajor, doctorIntroduction, DoctorLevel.valueOf(doctorLevel), iconUrl, doctorDepartmentId);
        this.department = new Department(departmentId, departmentName, departmentAddress, departmentLocation, departmentFloor, Area.valueOf(area), departmentIntroduction);
    }

    @ApiModelProperty("医生信息")
    private Doctor doctor;
    @ApiModelProperty("医生所在科室")
    private Department department;
}
