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

    public static final String DEFAULT_DESCRIPTION = SmsSendResult.class + "未初始化";

    private Boolean result;  // 发送是否成功

    private String description;  // 本条短信描述

    public SmsSendResult() {
        this(false, null);
    }

    public SmsSendResult(Boolean result, String description) {
        this.result = result;
        this.description = description;
    }
}
