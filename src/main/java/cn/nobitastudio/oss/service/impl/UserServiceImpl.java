package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.HealthArticle;
import cn.nobitastudio.oss.entity.MedicalCard;
import cn.nobitastudio.oss.entity.Role;
import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.model.dto.ModifyUserMobileDTO;
import cn.nobitastudio.oss.model.dto.ModifyUserPasswordDTO;
import cn.nobitastudio.oss.model.enumeration.HealthArticleType;
import cn.nobitastudio.oss.model.error.ErrorCode;
import cn.nobitastudio.oss.model.vo.DoctorAndDepartment;
import cn.nobitastudio.oss.model.vo.UserLoginResult;
import cn.nobitastudio.oss.repo.*;
import cn.nobitastudio.oss.service.inter.UserService;
import cn.nobitastudio.oss.util.CommonUtil;
import cn.nobitastudio.oss.model.vo.UserQueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/10 17:34
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value(value = "${oss.app.healthArticle:6}")
    private Integer defaultHealthArticle;

    @Inject
    private UserRepo userRepo;
    @Inject
    private RoleRepo roleRepo;
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private CollectDoctorRepo collectDoctorRepo;
    @Inject
    private HealthArticleRepo healthArticleRepo;
    @Inject
    private MedicalCardRepo medicalCardRepo;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        Assert.notNull(mobile, "未传入手机号");
        User user = userRepo.findByMobile(mobile).orElseThrow(() -> new UsernameNotFoundException("未查找到该用户"));
        // 查询user对应的权限
        List<GrantedAuthority> simpleGrantedAuthorities = getSimpleGrantedAuthorities(user);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getMobile(), user.getPassword(), user.getEnable()
                , Boolean.TRUE, Boolean.TRUE, user.getUnlocked(), simpleGrantedAuthorities);
        return userDetails;
    }

    /**
     * 得到指定用户的素有角色所对应的权限
     *
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
    public User add(User user) {
        // 检查该手机号是否已被注册
        if (userRepo.findByMobile(user.getMobile()).isPresent()) {
            throw new AppException("该手机号已注册",ErrorCode.MOBILE_HAS_REGISTER);
        }
        // 创建用户 初始化
        user.init(passwordEncoder);
        return userRepo.save(user);
    }

    /**
     * 更改用户基本信息
     *
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
    public User modifyPasswordByOldPassword(ModifyUserPasswordDTO modifyUserPasswordDTO) {
        User oldUser = userRepo.findById(modifyUserPasswordDTO.getUserId()).orElseThrow(() -> new AppException("未查找到该用户"));
        if (!passwordEncoder.matches(modifyUserPasswordDTO.getOldPassword(), oldUser.getPassword())) {
            throw new AppException("原密码错误,请重试");
        }
        oldUser.setPassword(passwordEncoder.encode(modifyUserPasswordDTO.getNewPassword()));
        return userRepo.save(oldUser);
    }

    /**
     * 用户
     *
     * @param modifyUserMobileDTO
     * @return
     */
    @Override
    public User modifyMobile(ModifyUserMobileDTO modifyUserMobileDTO) {
        return null;
    }

    /**
     * 用户(更改)找回密码
     * user中的新密码已是新密码,通过短信验证码进行验证，无需校验原密码
     *
     * @param user
     * @return
     */
    @Override
    public User modifyPasswordBySms(User user) {
        User oldUser = userRepo.findByMobile(user.getMobile()).orElseThrow(() -> new AppException("未查找到指定用户",ErrorCode.NOT_FIND_USER_BY_MOBILE));
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(oldUser);
    }

    /**
     * 用户更改绑定的手机号
     * 已经经过校验,直接更新为新绑定的手机号
     *
     * @param user
     * @return
     */
    @Override
    public User modifyMobile(User user) {
        User oldUser = userRepo.findById(user.getId()).orElseThrow(() -> new AppException("未查找到指定用户"));
        oldUser.setMobile(user.getMobile());
        return userRepo.save(oldUser);
    }

    /**
     * 用户登录成功后调用
     *
     * @return
     */
    @Override
    public UserLoginResult loginSuccess(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new AppException("未查找到指定用户"));
        user.wipeOffPassword(); // 擦除密码等敏感数据
        List<MedicalCard> medicalCards = medicalCardRepo.findBindMedicalCards(userId);  // 查询绑定的诊疗卡
        List<Object[]> objects = collectDoctorRepo.findDoctorAndDepartmentByUserId(userId);
        List<DoctorAndDepartment> doctorAndDepartments = CommonUtil.castEntity(objects, DoctorAndDepartment.class,
                Arrays.asList(new CommonUtil.DefaultClass(2, String.class), new CommonUtil.DefaultClass(3, String.class),
                        new CommonUtil.DefaultClass(4, String.class), new CommonUtil.DefaultClass(13, String.class))); // 查询用户收藏的医生机器医生对应的科室封装信息
        Pageable pageable = PageRequest.of(0, defaultHealthArticle, Sort.by(Sort.Direction.DESC, "publishTime"));
        List<HealthArticle> healthArticles = new ArrayList<>();
        healthArticles.addAll(healthArticleRepo.findByType(HealthArticleType.HOSPITAL_ACTIVITY, pageable));// 医院活动,用于显示轮播图
        healthArticles.addAll(healthArticleRepo.findByType(HealthArticleType.HEADLINE, pageable));// 健康头条
        healthArticles.addAll(healthArticleRepo.findByType(HealthArticleType.DOCTOR_LECTURE, pageable)); // 专家讲座
        return new UserLoginResult(user, medicalCards, doctorAndDepartments, healthArticles);
    }


}
