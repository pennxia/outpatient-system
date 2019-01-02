package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import cn.nobitastudio.oss.vo.UserCreateVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/10 17:34
 * @description
 */
@Data
@Entity
@Table(name = "user")
@Getter
@Setter
public class User implements Serializable,UserDetails {

	private static final long serialVersionUID = -2738521069482377465L;

    public User() {
    }

    public User(UserCreateVO userCreateVO, String sha256Password, String salt) {
        this.mobile = userCreateVO.getMobile();
        this.password = sha256Password;
        this.name = userCreateVO.getUsername();
        this.salt = salt;
        this.lastChangePassword = LocalDateTime.now();
        this.locked = Boolean.FALSE;
        this.enable = Boolean.TRUE;
    }

    @ApiModelProperty("用户Id")
    @Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
	private Integer id;

    @ApiModelProperty("联系方式")
    @Column(name = "mobile")
    @Equal
    private String mobile;

    @ApiModelProperty("用户密码")
    @Column(name = "password")
    private String password;

    @ApiModelProperty("用户名")
    @Column(name = "name")
    @Like
    private String name;

    @ApiModelProperty("盐")
    @Column(name = "salt")
    private String salt;

    @ApiModelProperty("上一次更改用户密码的时间")
    @Column(name = "last_change_password")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastChangePassword;

    @ApiModelProperty("用户是否锁定(0:未锁定,1:锁定)")
    @Column(name = "locked")
    private Boolean locked;

    @ApiModelProperty("用户是否启用(0:未启用,1:启用)")
    @Column(name = "enable")
    private Boolean enable;

    /**
     * 认证时调用,返回登录用户所对应的权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return name;
    }

    /**
     * 用户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * 用户是否被锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     * 凭证（密码）是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * 是否是启用状态
     * @return
     */
    @Override
    public boolean isEnabled() {
        return false;
    }
}
