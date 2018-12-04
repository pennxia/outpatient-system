package cn.nobitastudio.oss.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user")
@Getter
@Setter
public class User implements Serializable {

	private static final long serialVersionUID = -2738521069482377465L;

    @Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("用户Id")
	private Integer id;

    @Column(name = "phone")
    @ApiModelProperty("联系方式")
    private String phone;

    @Column(name = "password")
    @ApiModelProperty("用户密码")
    private String password;

    @Column(name = "username")
    @ApiModelProperty("用户名")
    private String username;

    @Column(name = "salt")
    @ApiModelProperty("盐")
    private String salt;
}
