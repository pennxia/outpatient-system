package cn.nobitastudio.oss.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "cba_user")
public class User implements Serializable {

	private static final long serialVersionUID = -2738521069482377465L;

    public User() {
    }

    @Column(name = "user_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("用户Id")
	private Long userId;

    @Column(name = "username")
    @ApiModelProperty("用户名")
    private String username;

    @Column(name = "password")
	@ApiModelProperty("用户密码")
    private String password;

    @Column(name = "salt")
    @ApiModelProperty("盐")
    private String salt;

    @Column(name = "code")
    @ApiModelProperty("用户代码")
    private String code;

    @Column(name = "phone")
    @ApiModelProperty("用户手机号")
    private String phone;

    @Column(name = "email")
    @ApiModelProperty("用户邮箱")
    private String email;

    @Column(name = "dept_id")
    @ApiModelProperty("用户部门")
    private Integer deptId;

    @Column(name = "duty")
    @ApiModelProperty("1:录入员 2:复核员 3:审核员")
    private Integer duty;

}
