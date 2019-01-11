package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.Contain;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:01
 * @description
 */
public interface ContainRepo extends CrudRepository<Contain,Integer>,JpaSpecificationExecutor {
}
