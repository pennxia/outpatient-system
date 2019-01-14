package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User,Integer>,JpaSpecificationExecutor {

    // 登录时调用
    Optional<User> findByMobile(String mobile);

}
