package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.Bind;
import cn.nobitastudio.oss.entity.ElectronicCase;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 14:47
 * @description
 */
public interface ElectronicCaseRepo extends CrudRepository<ElectronicCase, Integer>, JpaSpecificationExecutor {

    Optional<ElectronicCase> findByRegistrationRecordId(String registrationRecordId);

}
