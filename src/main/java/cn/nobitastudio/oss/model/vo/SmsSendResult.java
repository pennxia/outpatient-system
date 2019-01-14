package cn.nobitastudio.oss.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/04 14:41
 * @description
 */
@Getter
@Setter
public class SmsSendResult {

    private Boolean result;

    private Integer verificationCode;

    private String description;

    public SmsSendResult() {
        this(false,null,null);
    }

    public SmsSendResult(Boolean result, Integer verificationCode, String description) {
        this.result = result;
        this.verificationCode = verificationCode;
        this.description = description;
    }
}
