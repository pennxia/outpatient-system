package cn.nobitastudio.oss.config;

import cn.nobitastudio.oss.service.inter.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/12 12:41
 * @description 对于接口的访问权限进行控制
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Inject
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 鉴权时进行调用
     * 其中默认配置的 /** 映射到 /static （或/public、/resources、/META-INF/resources）
     * 其中默认配置的 /webjars/** 映射到 classpath:/META-INF/resources/webjars/
     * 请求相关网页的时候,不要带上/public /resources /META-INF/resources
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/html/login.html") // 网页登录地址
                .loginProcessingUrl("/authentication/login") // 登录发起的请求
                //.defaultSuccessUrl("/swagger-ui.html") // 登陆成功后跳转
                //.usernameParameter("myusername").passwordParameter("mypassword") 提交的username 和 password的参数名
                .and()
                .authorizeRequests()
                .antMatchers("/html/login.html").permitAll() // 登录页面痛
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}
