package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.oss.entity.CcTemp;
import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.repo.CcTempRepo;
import cn.nobitastudio.oss.service.inter.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/user")
public class UserController {

    @Inject
    private CcTempRepo ccTempRepo;
    @Inject
    private UserService userService;

    @GetMapping("/{id}")
    @ApiOperation("通过用户id获取用户信息")
    public ServiceResult<User> getById(@PathVariable(name = "id") Integer id) {
        try {
            return ServiceResult.success(userService.getById(id));
        } catch (Exception e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @GetMapping("/phone/{phone}")
    @ApiOperation("通过指定手机号的用户信息")
    public ServiceResult<User> getByPhone(@PathVariable(name = "phone") String phone) {
        try {
            return ServiceResult.success(userService.getByPhone(phone));
        } catch (Exception e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

}
