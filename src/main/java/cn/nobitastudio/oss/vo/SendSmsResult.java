package cn.nobitastudio.oss.vo;

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
public class SendSmsResult {

    private Boolean result;

    private Integer verificationCode;

    private String description;

    public SendSmsResult() {
        this(false,null,null);
    }

    public SendSmsResult(Boolean result, Integer verificationCode, String description) {
        this.result = result;
        this.verificationCode = verificationCode;
        this.description = description;
    }
}
