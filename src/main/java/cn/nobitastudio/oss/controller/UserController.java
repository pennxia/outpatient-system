package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.util.PageData;
import cn.nobitastudio.oss.entity.CcTemp;
import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.repo.CcTempRepo;
import cn.nobitastudio.oss.service.inter.UserService;
import cn.nobitastudio.oss.shiro.ShiroUtils;
import cn.nobitastudio.oss.vo.UserLoginVO;
import cn.nobitastudio.oss.vo.UserQueryVO;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

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
        } catch (Exception e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("通过指定手机号的用户信息")
    @GetMapping("/{phone}/phone")
    public ServiceResult<User> getByPhone(@PathVariable(name = "phone") String phone) {
        try {
            return ServiceResult.success(userService.getByPhone(phone));
        } catch (Exception e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("用户登录,登陆成功后,用户信息保存在session中")
    @PostMapping("/login")
    public ServiceResult<User> login(@RequestBody UserLoginVO userLoginVO) {
        try {
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userLoginVO.getPhone(), userLoginVO.getPassword());
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            return ServiceResult.failure(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return ServiceResult.failure("账号或密码不正确");
        } catch (LockedAccountException e) {
            return ServiceResult.failure("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return ServiceResult.failure("账户验证失败");
        }
        //验证成功
        ShiroUtils.getSession().setTimeout(1800000);  // session 30分钟
        try {
            return ServiceResult.success(userService.getByPhone(userLoginVO.getPhone()));
        } catch (Exception e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("用户注销登录")
    @GetMapping
    public void logout() {
        // 会清除session.
        ShiroUtils.logout();
    }

    @ApiOperation("对用户进行查询")
    @PostMapping("/list")
    public ServiceResult<PageImpl<User>> query(@RequestBody UserQueryVO userQueryVO, PageRequest pageRequest) {
        return ServiceResult.success(userService.query(userQueryVO,pageRequest));
    }

    @ApiOperation("用户注册,或者新增用户")
    @PostMapping("/register")
    public ServiceResult<User> register()


}
