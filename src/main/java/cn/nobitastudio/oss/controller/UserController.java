package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.service.inter.UserService;
import cn.nobitastudio.oss.shiro.ShiroUtils;
import cn.nobitastudio.oss.vo.UserCreateVO;
import cn.nobitastudio.oss.vo.UserQueryVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

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

    @ApiOperation("用户注销登录")
    @GetMapping("/logout")
    public void logout() {
        // 会清除session.
        ShiroUtils.logout();
    }

    @ApiOperation("在用户登录情况下通过session获取用户信息")
    @GetMapping("/info")
    public ServiceResult<User> info() {
        User user = (User) ShiroUtils.getSubject().getPrincipal();
        if (user == null) {
            return ServiceResult.failure("用户尚未登录");
        }
        return ServiceResult.success(user);
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

}
