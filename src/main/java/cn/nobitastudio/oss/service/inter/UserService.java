package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Role;
import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.vo.UserCreateVO;
import cn.nobitastudio.oss.vo.UserQueryVO;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public interface UserService {


    User getById(Integer id);

    User getByPhone(String phone);

    PageImpl<User> query(UserQueryVO userQueryVO, Pager pager);

    User add(UserCreateVO userCreateVO) throws IllegalAccessException;

    List<Role> getRoles(Integer userId);
}
