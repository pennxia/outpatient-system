package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.OSSOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:01
 * @description
 */
public interface OSSOrderRepo extends CrudRepository<OSSOrder,String>,JpaSpecificationExecutor {

    @Query(value = "select o.* from registration_record r,contain c,oss_order o" +
            " where r.id = ?1 and r.id = c.item_id and c.item_type = 'REGISTRATION_RECORD' and c.order_id = o.id",nativeQuery = true)
    Optional<OSSOrder> findByRegistrationId(String registrationId);
}
