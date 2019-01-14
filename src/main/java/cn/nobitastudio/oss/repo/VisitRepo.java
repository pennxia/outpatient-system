package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.Visit;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 12:54
 * @description
 */
public interface VisitRepo extends CrudRepository<Visit, Integer>, JpaSpecificationExecutor {

    Optional<Visit> findByDoctorId(Integer doctorId);
}
