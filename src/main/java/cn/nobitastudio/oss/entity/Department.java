package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import cn.nobitastudio.oss.model.enumeration.Area;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:14
 * @description 科室实例
 */
@Data
@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department implements Serializable {

    private static final long serialVersionUID = -3712970229472760165L;

    public interface DepartmentSimpleView {};
    public interface DepartmentDetailView extends DepartmentSimpleView {};

    @ApiModelProperty("科室号,id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    private Integer id;

    @ApiModelProperty("科室名称")
    @Column(name = "name")
    @Like
    @JsonView(DepartmentSimpleView.class)
    private String name;

    @ApiModelProperty("科室地址")
    @Column(name = "address")
    @Like
    @JsonView(DepartmentSimpleView.class)
    private String address;

    @ApiModelProperty("科室顺序（第x科室）")
    @Column(name = "location")
    @Equal
    @JsonView(DepartmentSimpleView.class)
    private Integer location;

    @ApiModelProperty("科室楼层")
    @Column(name = "floor")
    @Equal
    @JsonView(DepartmentDetailView.class)
    private Integer floor;

    @ApiModelProperty("科室所在区域（a/b/c 区等）")
    @Column(name = "area")
    @Enumerated(EnumType.STRING)
    @Equal
    @JsonView(DepartmentDetailView.class)
    private Area area;

    @ApiModelProperty("科室介绍")
    @Column(name = "introduction")
    @JsonView(DepartmentDetailView.class)
    private String introduction;
}
