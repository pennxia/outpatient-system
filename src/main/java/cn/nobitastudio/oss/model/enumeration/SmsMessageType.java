package cn.nobitastudio.oss.model.enumeration;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/12 12:35
 * @description
 */
@Getter
@Setter
public class SmsMessageType {

    private String random;

    public SmsMessageType() {
        random = UUID.randomUUID().toString();
    }
}
