package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import cn.nobitastudio.oss.model.enumeration.DoctorLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 13:40
 * @description 医生实例
 */
@Data
@Entity
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor implements Serializable {

    private static final long serialVersionUID = -1767052683247139655L;

    @ApiModelProperty("医生id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    private Integer id;

    @ApiModelProperty("医生姓名")
    @Column(name = "name")
    @Like
    private String name;

    @ApiModelProperty("特长")
    @Column(name = "specialty")
    @Equal
    private String specialty;

    @ApiModelProperty("亚专业")
    @Column(name = "subMajor")
    @Equal
    private String subMajor;

    @ApiModelProperty("医生介绍")
    @Column(name = "introduction")
    private String introduction;

    @ApiModelProperty("医生级别")
    @Column(name = "level")
    @Equal
    @Enumerated(EnumType.STRING)
    private DoctorLevel level;

    @ApiModelProperty("医生头像url:/pic/doctor/doctor1.png")
    @Column(name = "icon_url")
    private String iconUrl;

    @ApiModelProperty("所属科室")
    @Column(name = "department_id")
    @Equal
    private Integer departmentId;
}
