package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.CollectDoctor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/04 16:43
 * @description
 */
public interface CollectDoctorRepo extends CrudRepository<CollectDoctor, Integer>, JpaSpecificationExecutor {

    Optional<CollectDoctor> findByUserIdAndDoctorId(Integer userId, Integer doctorId);
}
