package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import cn.nobitastudio.oss.model.enumeration.Area;
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
 * @description 检查项实例
 */
@Data
@Entity
@Table(name = "check_item")
@Getter
@Setter
public class CheckItem implements Serializable {

    private static final long serialVersionUID = -4213321743396895666L;

    @ApiModelProperty("检查项id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    private Integer id;

    @ApiModelProperty("检查项名称")
    @Column(name = "name")
    @Like
    private String name;

    @ApiModelProperty("检查室地址")
    @Column(name = "room_address")
    @Like
    private String roomAddress;

    @ApiModelProperty("检查室顺序（第x检查室）")
    @Column(name = "room_location")
    @Equal
    private Integer roomLocation;

    @ApiModelProperty("检查室楼层")
    @Column(name = "room_floor")
    @Equal
    private Integer roomFloor;

    @ApiModelProperty("检查室在区域（a/b/c 区等）")
    @Column(name = "room_area")
    @Enumerated(EnumType.STRING)
    @Equal
    private Area area;

    @ApiModelProperty("检查项单价")
    @Column(name = "price")
    @Equal
    private Double price;

    @ApiModelProperty("检查医生id")
    @Column(name = "doctor_id")
    @Equal
    private Integer doctorId;
}
