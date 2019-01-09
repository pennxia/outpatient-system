package cn.nobitastudio.oss.config;

import cn.nobitastudio.oss.service.inter.UserService;
import cn.nobitastudio.oss.vo.enumeration.RoleName;
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

    @Inject
    private UserService userService;
    @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder).
                withUser("test").password(passwordEncoder.encode("test")).roles("ADMIN","USER","DB_ADMIN"); // 测试用户.拥有所有权限
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);

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
//                .loginPage("/html/login.html") // 网页登录地址
//                .loginProcessingUrl("/user/login") // 发起验证的请求
//                .defaultSuccessUrl("/swagger-ui.html") // 登陆成功后跳转
//                .usernameParameter("myusername").passwordParameter("mypassword")  // 验证请求的用户名和密码参数的username 和 password的参数名
                .loginPage("/user/login")
                .loginProcessingUrl("/auth")
                .and()
                .authorizeRequests()
                .antMatchers("/user/login").permitAll() // 登录页面请求
                .antMatchers("/html/login.html").permitAll()
                .antMatchers("/test").hasRole(RoleName.ADMIN.name()) // 控制权限
                .anyRequest().hasRole(RoleName.USER.name())
                .and()
                .csrf().disable();
    }
}
