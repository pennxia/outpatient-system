package cn.nobitastudio.oss.model.dto;

import java.io.Serializable;
import java.util.List;

import cn.nobitastudio.oss.entity.Department;
import cn.nobitastudio.oss.entity.Doctor;
import cn.nobitastudio.oss.entity.Visit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/15 22:57
 * @description 医生以及对应的科室以及号源信息 -- 从我的收藏进入时
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDepartmentVisitsDTO implements Serializable {

    private static final long serialVersionUID = 559299959620878568L;

    // 医生信息
    private Doctor doctor;
    // 科室信息
    private Department department;
    // 对应的号源信息
    private List<Visit> visits;
}
