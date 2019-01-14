package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/12 13:38
 * @description
 */
public interface RoleRepo extends CrudRepository<Role,Integer>,JpaSpecificationExecutor<Role> {

    @Query(value = "select * from Role where id in (select role_id from user_role where user_id = ?1)",nativeQuery = true)
    List<Role> queryRolesByUserId(Integer userId);

}
