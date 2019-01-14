package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Bind;
import cn.nobitastudio.oss.repo.BindRepo;
import cn.nobitastudio.oss.service.inter.BindService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 14:52
 * @description
 */
@Service
public class BindServiceImpl implements BindService {

    private static final String DELETE_SUCCESS = "绑定关系删除成功";
    private static final String SAVE_OR_UPDATE_SUCCESS = "绑定关系添加或修改成功";
    private static final String UNBIND_SUCCESS = "解绑成功";

    @Inject
    private BindRepo bindRepo;
    @Value(value = "${oss.app.maxBindCount}")
    private Integer maxBindCount;

    /**
     * 查询指定id绑定关系
     *
     * @param id 指定绑定关系id
     * @return
     */
    @Override
    public Bind getById(Integer id) {
        return bindRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定绑定关系"));
    }

    /**
     * 查询所有绑定关系,结果进行分页
     *
     * @param bind
     * @param pager 分页参数
     * @return
     */
    @Override
    public PageImpl<Bind> getAll(Bind bind, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(), pager.getLimit(), Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Bind> binds = bindRepo.findAll(SpecificationBuilder.toSpecification(bind), pageable);
        return new PageImpl<>(binds.getContent(), pageable, binds.getTotalElements());
    }

    /**
     * 删除指定id绑定关系
     *
     * @param id 指定绑定关系id
     * @return
     */
    @Override
    public String delete(Integer id) {
        bindRepo.delete(bindRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定绑定关系信息")));
        return DELETE_SUCCESS;
    }

    /**
     * 新增或更新绑定信息
     *
     * @param bind 待新增或更新的绑定信息
     * @return
     */
    @Override
    public Bind save(Bind bind) {
        bind.init();
        return bindRepo.save(bind);
    }

    /**
     * 用户绑定诊疗卡
     *
     * @param bind
     * @return
     */
    @Transactional
    @Override
    public Bind bind(Bind bind) {
        if (bindRepo.findByUserIdAndMedicalCardId(bind.getUserId(),bind.getMedicalCardId()).isPresent()){
            throw new AppException("您已绑定该诊疗卡");
        }
        Integer medicalCardBindCount = bindRepo.countAllByMedicalCardId(bind.getMedicalCardId());
        if (medicalCardBindCount.equals(maxBindCount)) {
            // 诊疗卡绑定数已上限
            throw new AppException("绑定失败,该诊疗卡绑定数已上限");
        }
        Integer mobileBindCount = bindRepo.countAllByUserId(bind.getUserId());
        if (medicalCardBindCount.equals(maxBindCount)) {
            // 号码绑定数已上限
            throw new AppException("绑定失败,该号码绑定数已上限");
        }
        return bindRepo.save(bind);
    }

    /**
     * 用户解绑诊疗卡
     *
     * @param bind 待解绑参数
     * @return
     */
    @Override
    public String unbind(Bind bind) {
        bindRepo.delete(bindRepo.findByUserIdAndMedicalCardId(bind.getUserId(),bind.getMedicalCardId()).orElseThrow(() -> new AppException("未查找到指定绑定关系")));
        return UNBIND_SUCCESS;
    }
}
