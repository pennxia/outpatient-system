package cn.nobitastudio.oss.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/19 15:17
 * @description 标准的消息传送
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardInfo {

    @ApiModelProperty("消息")
    private String info;
}
