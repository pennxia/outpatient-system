package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.RegistrationRecord;
import cn.nobitastudio.oss.repo.RegistrationRecordRepo;
import cn.nobitastudio.oss.service.inter.RegistrationRecordService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/06 15:13
 * @description
 */
@Service
public class RegistrationRecordServiceImpl implements RegistrationRecordService {

    private static final String DELETE_SUCCESS = "挂号记录信息删除成功";
    private static final String SAVE_OR_UPDATE_SUCCESS = "挂号记录信息添加或修改成功";

    @Inject
    private RegistrationRecordRepo registrationRecordRepo;

    /**
     * 查询指定id挂号记录信息
     *
     * @param id 挂号记录id
     * @return
     */
    @Override
    public RegistrationRecord getById(Integer id) {
        return registrationRecordRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定id的挂号记录"));
    }

    /**
     * 查询所有挂号记录,结果进行分页
     *
     * @param registrationRecord
     * @param pager 分页参数
     * @return
     */
    @Override
    public PageImpl<RegistrationRecord> getAll(RegistrationRecord registrationRecord, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(),pager.getLimit(),Sort.by(Sort.Direction.DESC,"createTime"));
        Page<RegistrationRecord> registrationRecords = registrationRecordRepo.findAll(SpecificationBuilder.toSpecification(registrationRecord),pageable);
        return new PageImpl<>(registrationRecords.getContent(),pageable,registrationRecords.getTotalElements());
    }

    /**
     * 删除指定挂号记录信息
     *
     * @param id 指定挂号记录id
     * @return
     */
    @Override
    public String delete(Integer id) {
        registrationRecordRepo.delete(registrationRecordRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定id的挂号记录")));
        return DELETE_SUCCESS;
    }

    /**
     * 新增或更新挂号记录
     *
     * @param registrationRecord 待新增或更新的挂号记录
     * @return
     */
    @Override
    public RegistrationRecord save(RegistrationRecord registrationRecord) {
        return registrationRecordRepo.save(registrationRecord);
    }

    /**
     * 用户进行挂号操作
     *
     * @param registrationRecord
     * @return
     */
    @Transactional
    @Override
    public RegistrationRecord register(RegistrationRecord registrationRecord) {
        // 待实现
        return null;
    }
}
