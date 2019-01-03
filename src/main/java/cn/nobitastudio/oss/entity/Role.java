package cn.nobitastudio.oss.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/12 13:29
 * @description 角色实例
 */
@Data
@Entity
@Table(name = "role")
@Getter
@Setter
public class Role implements Serializable {

    private static final long serialVersionUID = -2791309956740231708L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("角色Id")
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("角色名")
    private String name;

    @Column(name = "description")
    @ApiModelProperty("角色描述")
    private String description;
}
