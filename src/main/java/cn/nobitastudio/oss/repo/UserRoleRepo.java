package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.UserRole;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/09 11:43
 * @description
 */
public interface UserRoleRepo extends CrudRepository<UserRole, Integer>, JpaSpecificationExecutor {

    @Query(value = "select * from user_role where user_id = :#{#userRole.userId} and role_id = :#{#userRole.roleId}", nativeQuery = true)
    Optional<UserRole> findByUserRole(@Param(value = "userRole") UserRole userRole);

    @Modifying
    @Query(value = "delete from user_role where user_id = :#{#userRole.userId} and role_id = :#{#userRole.roleId}",nativeQuery = true)
    void deleteByUserRole(@Param(value = "userRole") UserRole userRole);

}
