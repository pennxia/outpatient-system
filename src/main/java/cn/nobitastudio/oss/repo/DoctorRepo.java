package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 13:54
 * @description
 */
public interface DoctorRepo extends CrudRepository<Doctor,Integer>,JpaSpecificationExecutor {

    @Query(value = "select d.* from doctor d,collect_doctor c where c.user_id = ?1 and c.doctor_id = d.id order by c.create_time desc",nativeQuery = true)
    List<Doctor> findCollectDoctor(Integer id);
}
