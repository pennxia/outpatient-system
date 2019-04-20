package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.CollectDoctor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/04 16:43
 * @description
 */
public interface CollectDoctorRepo extends CrudRepository<CollectDoctor, Integer>, JpaSpecificationExecutor {

    Optional<CollectDoctor> findByUserIdAndDoctorId(Integer userId, Integer doctorId);

    @Query(value = "select dt.id as doctor_id,dt.name as doctor_name,dt.specialty as doctor_specialty,dt.sub_major as sub_major," +
            "dt.introduction as doctor_introduction,dt.level as doctor_level,dt.icon_url as iconUrl,dt.department_id as doctor_department_id," +
            "dp.* " +
            "from doctor dt,department dp,collect_doctor cd where dt.department_id = dp.id and cd.user_id = ?1 and cd.doctor_id = dt.id order by cd.create_time desc;",nativeQuery = true)
    List<Object[]> findDoctorAndDepartmentByUserId(Integer userId);
}
