package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.model.dto.ModifyUserMobileDTO;
import cn.nobitastudio.oss.model.dto.ModifyUserPasswordDTO;
import cn.nobitastudio.oss.model.vo.UserQueryVO;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {


    /**
     * 查询指定userId的用户
     *
     * @param id
     * @return
     */
    User getById(Integer id);

    /**
     * 查询指定手机号的用户
     *
     * @param phone
     * @return
     */
    User getByMobile(String phone);

    /**
     * 基础分页查询用户
     *
     * @param userQueryVO
     * @param pager
     * @return
     */
    PageImpl<User> query(UserQueryVO userQueryVO, Pager pager);

    /**
     * 用户注册
     *
     * @param user
     * @return
     * @throws IllegalAccessException
     */
    User add(@JsonView(User.UserCreateView.class) User user) throws IllegalAccessException;

    /**
     * 用户修改基础信息,不可更改密码
     *
     * @param user
     * @return
     */
    User modify(User user);

    /**
     * 用户更新密码
     *
     * @param modifyUserPasswordDTO
     * @return
     */
    User modifyPasswordByOldPassword(ModifyUserPasswordDTO modifyUserPasswordDTO);

    /**
     * 用户更换绑定的手机号
     *
     * @param modifyUserMobileDTO
     * @return
     */
    User modifyMobile(ModifyUserMobileDTO modifyUserMobileDTO);

    /**
     * 用户(更改)找回密码
     * @param user
     * @return
     */
    User modifyPasswordBySms(@JsonView(User.UserFindPasswordView.class) User user);

    /**
     * 用户更改绑定的手机号
     * @param user
     * @return
     */
    User modifyMobile(@JsonView(User.UserModifyMobileView.class) User user);

}
