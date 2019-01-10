package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.Bind;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 14:47
 * @description
 */
public interface BindRepo extends CrudRepository<Bind, Integer>, JpaSpecificationExecutor {

    Integer countAllByMobile(String mobile);

    Integer countAllByMedicalCardId(String medicalCardId);

    Optional<Bind> findByMobileAndMedicalCardId(String mobile, String medicalCardId);
}
