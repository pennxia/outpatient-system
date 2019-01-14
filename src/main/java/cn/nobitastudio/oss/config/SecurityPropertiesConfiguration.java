package cn.nobitastudio.oss.config;

import cn.nobitastudio.oss.property.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/09 20:33
 * @description
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityPropertiesConfiguration {
}
