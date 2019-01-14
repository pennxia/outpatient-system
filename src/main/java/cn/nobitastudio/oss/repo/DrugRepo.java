package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.Drug;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:01
 * @description
 */
public interface DrugRepo extends CrudRepository<Drug,Integer>,JpaSpecificationExecutor {
}
