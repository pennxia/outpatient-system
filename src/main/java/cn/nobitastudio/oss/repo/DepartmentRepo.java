package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.Department;
import cn.nobitastudio.oss.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 12:39
 * @description
 */
public interface DepartmentRepo extends CrudRepository<Department,Integer>,JpaSpecificationExecutor {

    @Override
    Optional<Department> findById(Integer integer);
}
