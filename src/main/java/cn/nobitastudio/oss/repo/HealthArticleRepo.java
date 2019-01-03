package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.HealthArticle;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 17:58
 * @description
 */
public interface HealthArticleRepo  extends CrudRepository<HealthArticle,Integer>,JpaSpecificationExecutor {
}
