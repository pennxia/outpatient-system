package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import cn.nobitastudio.oss.model.vo.UserCreateVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
public class User implements Serializable {

    private static final long serialVersionUID = -2738521069482377465L;

    public interface UserCreateView {};


    public User() {
    }

    public User(UserCreateVO userCreateVO) {
        this.mobile = userCreateVO.getMobile();
        this.password = userCreateVO.getPassword();
        this.name = userCreateVO.getUsername();
        this.lastChangePassword = LocalDateTime.now();
        this.unlocked = Boolean.TRUE;
        this.enable = Boolean.TRUE;
        this.idCard = userCreateVO.getIdCard();
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
    private String idCard;

    /**
     * 用户更新
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
}
