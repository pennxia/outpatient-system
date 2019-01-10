package cn.nobitastudio.oss.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/09 20:29
 * @description
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "oss.app")
public class SecurityProperties {

    private BrowserProperties browser;
}
