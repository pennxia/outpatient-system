package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/10 17:34
 * @description 用户实例
 */
@Data
@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -2738521069482377465L;

    public interface UserCreateView {
    }  // 用户注册

    public interface UserModifyView extends UserCreateView {
    }  // 用户更改基础信息

    public interface UserModifyPasswordView {} //用户更改密码

    public interface UserModifyMobileView {} // 用户修改mobile

    @ApiModelProperty("用户Id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    @JsonView(UserModifyView.class)
    private Integer id;

    @ApiModelProperty("联系方式")
    @Column(name = "mobile")
    @Equal
    @JsonView(UserCreateView.class)
    private String mobile;

    @ApiModelProperty("用户密码")
    @Column(name = "password")
    @JsonView(UserCreateView.class)
    private String password;

    @ApiModelProperty("用户名")
    @Column(name = "name")
    @Like
    @JsonView(UserCreateView.class)
    private String name;

    @ApiModelProperty("上一次更改用户密码的时间")
    @Column(name = "last_change_password")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastChangePassword;

    @ApiModelProperty("用户是否锁定(0:未锁定,1:锁定)")
    @Column(name = "unlocked")
    private Boolean unlocked;

    @ApiModelProperty("用户是否启用(0:未启用,1:启用)")
    @Column(name = "enable")
    private Boolean enable;

    @ApiModelProperty("用户是否启用(0:未启用,1:启用)")
    @Column(name = "id_card")
    @JsonView(UserCreateView.class)
    private String idCard;

    /**
     * 用户更新 仅更新用户名以及身份证号
     *
     * @param user
     * @return
     */
    public User update(User user) {
        if (user.getName() != null) {
            this.setName(user.getName());
        }
        if (user.getIdCard() != null) {
            this.setIdCard(user.getIdCard());
        }
        return this;
    }

    /**
     * 用户创建时调用
     *
     * @param passwordEncoder
     * @return
     */
    public void init(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        this.lastChangePassword = LocalDateTime.now();
        this.enable = Boolean.TRUE;
        this.unlocked = Boolean.TRUE;
    }
}
