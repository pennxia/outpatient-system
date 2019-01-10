package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/09 11:42
 * @description
 */
@Data
@Entity
@Table(name = "user_role")
@Getter
@Setter
public class UserRole implements Serializable {

    private static final long serialVersionUID = 4254637288594977399L;

    @ApiModelProperty("用户绑定角色的物理id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    private Integer id;

    @ApiModelProperty("用户id")
    @Column(name = "user_id")
    @Equal
    private Integer userId;

    @ApiModelProperty("角色id")
    @Column(name = "role_id")
    @Equal
    private Integer roleId;
}
