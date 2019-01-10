package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import cn.nobitastudio.oss.model.enumeration.RoleName;
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

    @ApiModelProperty("角色Id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    private Integer id;

    @ApiModelProperty("角色名")
    @Column(name = "name")
    @Like
    @Enumerated(EnumType.STRING) // 指定枚举映射为数据库的策略按照 name 映射,默认是按照顺序
    private RoleName name;

    @ApiModelProperty("角色描述")
    @Column(name = "description")
    @Equal
    private String description;
}
