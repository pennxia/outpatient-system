package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.property.SecurityProperties;
import cn.nobitastudio.oss.service.inter.UserService;
import cn.nobitastudio.oss.model.UserCreateVO;
import cn.nobitastudio.oss.model.UserQueryVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/10 17:34
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Inject
    private UserService userService;
    @Inject
    private SecurityProperties securityProperties;

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @ApiOperation("通过用户id获取用户信息")
    @GetMapping("/{id}")
    public ServiceResult<User> getById(@PathVariable(name = "id") Integer id) {
        try {
            return ServiceResult.success(userService.getById(id));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("通过指定手机号的用户信息")
    @GetMapping("/{mobile}/mobile")
    public ServiceResult<User> getByPhone(@PathVariable(name = "mobile") String mobile) {
        try {
            return ServiceResult.success(userService.getByMobile(mobile));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("用户登录请求")
    @GetMapping("/login")
    public ServiceResult<String> login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            System.out.println("引发跳转的请求是：" + targetUrl);
            if (StringUtils.endsWith(targetUrl,".html")) {
                // 网页跳转
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
            }
        }
        return ServiceResult.failure("访问的服务需要身份认证,请引导用户到登录页");
    }

    @ApiOperation("用户验证")
    @PostMapping("/auth")
    public ServiceResult<User> auth(@RequestBody User user) {
        return null;
    }

    @ApiOperation("用户注销登录")
    @GetMapping("/logout")
    public void logout() {
        // 会清除session.
    }

    @ApiOperation("在用户登录情况下通过session获取用户信息")
    @GetMapping("/info")
    public ServiceResult<User> info() {
        return null;
    }

    @ApiOperation("对用户进行查询")
    @PostMapping("/list")
    public ServiceResult<PageImpl<User>> query(@RequestBody UserQueryVO userQueryVO, Pager pager) {
        return ServiceResult.success(userService.query(userQueryVO, pager));
    }

    @ApiOperation("用户注册,或者新增用户")
    @PostMapping("/register")
    public ServiceResult<User> register(@RequestBody UserCreateVO userCreateVO) {
        try {
            return ServiceResult.success(userService.add(userCreateVO));
        } catch (Exception e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("获取认证信息")
    @GetMapping("/auth-info")
    public ServiceResult<Authentication> getAuthInfo() {
        return ServiceResult.success(SecurityContextHolder.getContext().getAuthentication());
    }

}
