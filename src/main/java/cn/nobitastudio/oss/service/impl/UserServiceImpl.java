package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Role;
import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.model.dto.ModifyUserPasswordDTO;
import cn.nobitastudio.oss.repo.RoleRepo;
import cn.nobitastudio.oss.repo.UserRepo;
import cn.nobitastudio.oss.service.inter.UserService;
import cn.nobitastudio.oss.util.CommonUtil;
import cn.nobitastudio.oss.model.vo.UserCreateVO;
import cn.nobitastudio.oss.model.vo.UserQueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/10 17:34
 * @description
 */
@Service
public class UserServiceImpl implements UserService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private UserRepo userRepo;
    @Inject
    private RoleRepo roleRepo;
    @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        Assert.notNull(mobile,"未传入手机号");
        User user = userRepo.findByMobile(mobile).orElseThrow(() -> new UsernameNotFoundException("未查找到该用户"));
        // 查询user对应的权限
        List<GrantedAuthority> simpleGrantedAuthorities = getSimpleGrantedAuthorities(user);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getMobile(),user.getPassword(),user.getEnable()
                ,Boolean.TRUE,Boolean.TRUE,user.getUnlocked(),simpleGrantedAuthorities);
        return userDetails;
    }

    /**
     * 得到指定用户的素有角色所对应的权限
     * @param user
     * @return
     */
    private List<GrantedAuthority> getSimpleGrantedAuthorities(User user) {
        List<Role> roles = roleRepo.queryRolesByUserId(user.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles.size());
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().name())); // 添加权限,，对应于SpringSecurity的权限判断是 hasAuthority
        }
        return grantedAuthorities;
    }

    @Override
    public User getById(Integer id) {
        logger.info(passwordEncoder.encode("user"));
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
        // 创建用户 1.加密密码(spring security 将盐值自动放入密码中)
        userCreateVO.setPassword(passwordEncoder.encode(userCreateVO.getPassword()));
        User user = new User(userCreateVO);
        return userRepo.save(user);
    }

    /**
     * 更改用户基本信息
     * @param user
     * @return
     */
    @Override
    public User modify(User user) {
        User oldUser = userRepo.findById(user.getId()).orElseThrow(() -> new AppException("未查找到该用户"));
        oldUser.update(user);
        return userRepo.save(oldUser);
    }

    /**
     * 用户更新密码
     *
     * @param modifyUserPasswordDTO
     * @return
     */
    @Override
    public User modifyPassword(ModifyUserPasswordDTO modifyUserPasswordDTO) {
        User oldUser = userRepo.findById(modifyUserPasswordDTO.getUserId()).orElseThrow(() -> new AppException("未查找到该用户"));
        if (!passwordEncoder.matches(modifyUserPasswordDTO.getOldPassword(),oldUser.getPassword())) {
            throw new AppException("原密码错误,请重试");
        }
        oldUser.setPassword(passwordEncoder.encode(modifyUserPasswordDTO.getNewPassword()));
        return userRepo.save(oldUser);
    }


}
