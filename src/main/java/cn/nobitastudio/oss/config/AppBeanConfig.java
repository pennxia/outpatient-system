package cn.nobitastudio.oss.config;

import cn.nobitastudio.oss.helper.SmsHelper;
import cn.nobitastudio.oss.helper.ValidateCodeContainHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/12 12:33
 * @description
 */
@Configuration
public class AppBeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }

}
