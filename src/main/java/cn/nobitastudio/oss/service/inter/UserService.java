package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.vo.UserQueryVO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public interface UserService {

    User getById(Integer id) throws Exception;

    User getByPhone(String phone) throws Exception;

    PageImpl<User> query(UserQueryVO userQueryVO, PageRequest pageRequest);
}
