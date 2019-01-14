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
@AllArgsConstructor
public class StandardMessage implements Serializable {

    private static final long serialVersionUID = -4310067203843221166L;

    public static final Integer UNKNOWN_FLAG = 0; // 默认为不知道是哪种类型
    public static final String UNKNOWN_DESCRIPTION = "该消息未初始化";

    public static final Integer VALIDATION_CODE_SEND_SUCCESS_FLAG = 1001;
    public static final String VALIDATION_CODE_SEND_SUCCESS_DESC = "验证码发送成功";
    public static final Integer VALIDATION_CODE_SEND_FAIL_FLAG = 1002;
    public static final String VALIDATION_CODE_SEND_FAIL_DESC = "验证码发送失败,请稍后再试";
    public static final Integer VALIDATION_CODE_CONFRIM_SUCCESS_FLAG = 1003;
    public static final String VALIDATION_CODE_CONFRIM_SUCCESS_DESC = "验证码验证成功";


    public StandardMessage() {
        flagCode = UNKNOWN_FLAG;
        description = UNKNOWN_DESCRIPTION;
    }

    /**
     * 1.订单已处于取消预约状态
     */
    @ApiModelProperty("标识码")
    private Integer flagCode;

    @ApiModelProperty("描述")
    private String description;
}
