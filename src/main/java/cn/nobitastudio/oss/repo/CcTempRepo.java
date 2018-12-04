package cn.nobitastudio.oss.repo;

import cn.nobitastudio.oss.entity.CcTemp;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CcTempRepo extends CrudRepository<CcTemp,Integer>,JpaSpecificationExecutor<CcTemp> {
}
