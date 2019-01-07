package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.Department;
import cn.nobitastudio.oss.vo.test.SimpleDepartmentVO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 12:39
 * @description
 */
public interface DepartmentRepo extends CrudRepository<Department, Integer>, JpaSpecificationExecutor {

    @Override
    Optional<Department> findById(Integer integer);

    @Query(value = "select d.id,d.area,d.introduction,c.name from department d,doctor c where d.id = :id and d.id = c.department_id",nativeQuery = true)
    List<Object[]> findSimpleDepartments(@Param("id") Integer id);
}
