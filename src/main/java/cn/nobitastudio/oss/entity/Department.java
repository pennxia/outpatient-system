package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import cn.nobitastudio.oss.vo.Enum.Area;
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
 * @description
 */
@Data
@Entity
@Table(name = "department")
@Getter
@Setter
public class Department implements Serializable {

    private static final long serialVersionUID = -3712970229472760165L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("科室号,id")
    @Equal
    private Integer id;

    @Column(name = "name", length = 100)
    @ApiModelProperty("科室名称")
    @Like
    private String name;

    @Column(name = "address", length = 100)
    @ApiModelProperty("科室地址")
    @Like
    private String address;

    @Column(name = "location", length = 100)
    @ApiModelProperty("科室顺序（第x科室）")
    @Equal
    private Integer location;

    @Column(name = "floor")
    @ApiModelProperty("科室楼层")
    @Equal
    private Integer floor;

    @Column(name = "area", length = 100)
    @ApiModelProperty("科室所在区域（a/b/c 区等）")
    @Equal
    private Area area;

    @Column(name = "introduction", length = 300)
    @ApiModelProperty("科室介绍")
    private String introduction;
}
