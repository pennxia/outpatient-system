package cn.nobitastudio.oss.vo;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
