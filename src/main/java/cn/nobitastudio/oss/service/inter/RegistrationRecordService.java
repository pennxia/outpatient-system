package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.RegistrationRecord;
import cn.nobitastudio.oss.model.dto.RegisterDTO;
import org.quartz.SchedulerException;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/06 15:13
 * @description
 */
public interface RegistrationRecordService {


    /**
     * 查询指定id挂号记录信息
     *
     * @param id 挂号记录id
     * @return
     */
    RegistrationRecord getById(String id);

    /**
     * 查询所有挂号记录,结果进行分页
     *
     * @param pager 分页参数
     * @return
     */
    PageImpl<RegistrationRecord> getAll(RegistrationRecord registrationRecord, Pager pager);

    /**
     * 删除指定挂号记录信息
     *
     * @param id 指定挂号记录id
     * @return
     */
    String delete(String id);

    /**
     * 新增或更新挂号记录
     *
     * @param registrationRecord 待新增或更新的挂号记录
     * @return
     */
    RegistrationRecord save(RegistrationRecord registrationRecord);

    /**
     * 用户进行挂号操作
     * @param registerDTO
     * @return
     */
    @Transactional
    RegistrationRecord register(RegisterDTO registerDTO) throws SchedulerException;
}
