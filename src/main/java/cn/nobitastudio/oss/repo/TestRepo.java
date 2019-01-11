package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.Test;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 14:47
 * @description
 */
public interface TestRepo extends CrudRepository<Test, String>, JpaSpecificationExecutor {

    @Query(value = "select count(*) from test",nativeQuery = true)
    Integer countAll();
}
