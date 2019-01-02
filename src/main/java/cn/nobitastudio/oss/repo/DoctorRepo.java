package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.Doctor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 13:54
 * @description
 */
public interface DoctorRepo extends CrudRepository<Doctor,Integer>,JpaSpecificationExecutor {
}
