package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.oss.entity.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/11 09:54
 * @description
 */
public interface TestService {

    @Transactional
    void test() ;

    String testConf();

    // 登录
    ServiceResult<User> login(User user);
}
