package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.oss.entity.User;

public interface UserService {

    User getById(Integer id) throws Exception;

    User getByPhone(String phone) throws Exception;
}
