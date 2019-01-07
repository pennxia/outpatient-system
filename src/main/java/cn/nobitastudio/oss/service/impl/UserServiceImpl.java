package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Role;
import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.repo.RoleRepo;
import cn.nobitastudio.oss.repo.UserRepo;
import cn.nobitastudio.oss.service.inter.UserService;
import cn.nobitastudio.oss.util.CommonUtil;
import cn.nobitastudio.oss.vo.UserCreateVO;
import cn.nobitastudio.oss.vo.UserQueryVO;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sun.misc.BASE64Encoder;

import javax.inject.Inject;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/10 17:34
 * @description
 */
@Service
public class UserServiceImpl implements UserService{

    @Inject
    private UserRepo userRepo;
    @Inject
    private RoleRepo roleRepo;

    @Override
    public User getById(Integer id) {
        return userRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定id的用户信息"));
    }

    @Override
    public User getByMobile(String mobile) {
        return userRepo.findByMobile(mobile).orElseThrow(() -> new AppException("未查找phone对应的用户信息"));
    }

    @Override
    public PageImpl<User> query(UserQueryVO userQueryVO, Pager pager) {
        Specification<User> spec = userQueryVO == null ? null : SpecificationBuilder.toSpecification(userQueryVO);
        Pageable pageable = PageRequest.of(pager.getPage(), pager.getLimit(), new Sort(Sort.Direction.ASC, "id"));
        Page<User> users = userRepo.findAll(spec, pageable);
        return new PageImpl<>(users.getContent(), pageable, users.getTotalElements());
    }

    @Override
    public User add(UserCreateVO userCreateVO) throws IllegalAccessException {
        //检查传递参数
        CommonUtil.checkObjectFieldIsNull(userCreateVO);
        // 检查该手机号是否已被注册
        if (userRepo.findByMobile(userCreateVO.getMobile()).isPresent()) {
            throw new AppException("该手机号已注册");
        }
        // 创建用户 1.加密密码,生成盐
        return null;
    }

    @Override
    public List<Role> getRoles(Integer userId) {
        return roleRepo.queryRolesByUserId(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        Assert.notNull(mobile,"未传入手机号");
        return userRepo.findByMobile(mobile).orElseThrow(() -> new UsernameNotFoundException("未查找到该用户"));
    }
}
