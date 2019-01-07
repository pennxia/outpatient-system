package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.MedicalCard;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:32
 * @description
 */
public interface MedicalCardRepo extends CrudRepository<MedicalCard,Integer>,JpaSpecificationExecutor {
}
