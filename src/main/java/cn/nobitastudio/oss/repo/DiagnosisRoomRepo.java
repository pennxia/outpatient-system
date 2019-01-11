package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.DiagnosisRoom;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 12:39
 * @description
 */
public interface DiagnosisRoomRepo extends CrudRepository<DiagnosisRoom, Integer>, JpaSpecificationExecutor {
}
