package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.HealthArticle;
import cn.nobitastudio.oss.model.enumeration.HealthArticleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 17:58
 * @description
 */
public interface HealthArticleRepo extends CrudRepository<HealthArticle, Integer>, JpaSpecificationExecutor {

    List<HealthArticle> findByType(HealthArticleType type, Pageable pageable);
}
