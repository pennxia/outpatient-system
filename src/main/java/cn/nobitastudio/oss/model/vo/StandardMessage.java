package cn.nobitastudio.oss.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/13 01:10
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardMessage implements Serializable {

    private static final long serialVersionUID = -4310067203843221166L;

    /**
     * 1.订单已处于取消预约状态
     */
    @ApiModelProperty("标识码")
    private Integer flagCode;

    @ApiModelProperty("描述")
    private String description;
}
