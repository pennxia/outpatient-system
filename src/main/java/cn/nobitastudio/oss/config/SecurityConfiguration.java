package cn.nobitastudio.oss.config;

import cn.nobitastudio.oss.authentication.hander.OSSAuthenticationFailureHandler;
import cn.nobitastudio.oss.authentication.hander.OSSAuthenticationSuccessHandler;
import cn.nobitastudio.oss.property.SecurityProperties;
import cn.nobitastudio.oss.service.inter.UserService;
import cn.nobitastudio.oss.model.enumeration.RoleName;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/12 12:41
 * @description 对于接口的访问权限进行控制
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private UserService userService;
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private SecurityProperties securityProperties;
    @Inject
    private OSSAuthenticationSuccessHandler ossAuthenticationSuccessHandler;
    @Inject
    private OSSAuthenticationFailureHandler ossAuthenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder).
                withUser("test").password(passwordEncoder.encode("test"))
                .roles(RoleName.USER.name(), RoleName.ADMIN.name(), RoleName.DB_ADMIN.name()) // 角色
                .authorities(RoleName.USER.name(), RoleName.ADMIN.name(), RoleName.DB_ADMIN.name()); //  权限
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
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http
////                .loginPage("/html/login.html") // 网页登录地址
////                .loginProcessingUrl("/user/login") // 发起验证的请求
////                .usernameParameter("myusername").passwordParameter("mypassword")  // 验证请求的用户名和密码参数的username 和 password的参数名
//                .authorizeRequests()
//                .anyRequest().permitAll();
        http.

                formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/auth")
//                .successHandler(ossAuthenticationSuccessHandler)  // 登陆成功后的处理器
//                .failureHandler(ossAuthenticationFailureHandler)  // 登陆失败后的处理器
                .and()
                .authorizeRequests()
//                .antMatchers("/swagger-ui.html").hasRole(RoleName.ADMIN.name()) // 控制权限，角色应该拥有ROLE_XXX 这样的角色才能调用该接口 默认使用hasAuthority
                .antMatchers("/user/login",securityProperties.getBrowser().getLoginPage()).permitAll()
                .antMatchers("/swagger-ui.html").hasAuthority(RoleName.USER.name()) // 控制权限,下面的接口若需要权限,有可能无法扫描
                .antMatchers("/**").hasAuthority(RoleName.USER.name()) // 控制所有接口调用都必须是普通用户才能调用
                .and()
                .csrf().disable();



    }
}
