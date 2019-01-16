package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.MedicalCard;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:32
 * @description
 */
public interface MedicalCardRepo extends CrudRepository<MedicalCard, String>, JpaSpecificationExecutor {

    @Query(value = "select mc.* from medical_card mc,user u,bind b where u.id = :userId and u.id = b.user_id and b.medical_card_id = mc.id",nativeQuery = true)
    List<MedicalCard> findBindMedicalCards(@Param(value = "userId") Integer userId);
}
