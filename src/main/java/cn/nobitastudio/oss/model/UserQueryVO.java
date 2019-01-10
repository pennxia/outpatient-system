package cn.nobitastudio.oss.model;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/10 17:34
 * @description
 */
@Getter
@Setter
public class UserQueryVO {

    @ApiModelProperty("用户的id")
    @Equal
    private Integer id;

    @ApiModelProperty("用户联系方式,登录所用的手机号")
    @Equal
    private String phone;

    @ApiModelProperty("用户名")
    @Like
    private String username;

}
