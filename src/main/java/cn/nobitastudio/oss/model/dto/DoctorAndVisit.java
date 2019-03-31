package cn.nobitastudio.oss.model.dto;

import cn.nobitastudio.oss.entity.Doctor;
import cn.nobitastudio.oss.entity.Visit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/30 22:27
 * @description 封装指定医生 以及 该医生未来一周(可控制指定天数.默认7天)的号源信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAndVisit {

    private Doctor doctor;

    private List<Visit> visits;
}
