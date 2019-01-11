package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import cn.nobitastudio.oss.model.enumeration.Area;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:14
 * @description 诊疗室实例
 */
@Data
@Entity
@Table(name = "diagnosis_room")
@Getter
@Setter
public class DiagnosisRoom implements Serializable {


    private static final long serialVersionUID = 2159462784859847255L;

    @ApiModelProperty("诊疗室id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    private Integer id;

    @ApiModelProperty("诊疗室名称")
    @Column(name = "name")
    @Like
    private String name;

    @ApiModelProperty("诊疗室地址，比如207诊疗室")
    @Column(name = "address")
    @Like
    private String address;

    @ApiModelProperty("诊疗室顺序（第x诊疗室）")
    @Column(name = "location")
    @Equal
    private Integer location;

    @ApiModelProperty("诊疗室楼层")
    @Column(name = "floor")
    @Equal
    private Integer floor;

    @ApiModelProperty("诊疗室所在区域（a/b/c 区等）")
    @Column(name = "area")
    @Enumerated(EnumType.STRING)
    @Equal
    private Area area;

    @ApiModelProperty("department_id")
    @Column(name = "department_id")
    @Equal
    private Integer departmentId;
}
