package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.MedicalCard;
import cn.nobitastudio.oss.repo.MedicalCardRepo;
import cn.nobitastudio.oss.service.inter.MedicalCardService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:36
 * @description
 */
@Service
public class MedicalCardServiceImpl implements MedicalCardService {

    private static final String DELETE_SUCCESS = "诊疗卡信息删除成功";
    private static final String SAVE_OR_UPDATE_SUCCESS = "诊疗卡信息添加或修改成功";

    @Inject
    private MedicalCardRepo medicalCardRepo;

    /**
     * 查询指定id诊疗卡信息
     *
     * @param id 指定诊疗卡id
     * @return
     */
    @Override
    public MedicalCard getById(Integer id) {
        return medicalCardRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定诊疗卡信息"));
    }

    /**
     * 查询指定诊疗卡号medicalCardNo的诊疗卡信息
     *
     * @param medicalCardNo 指定诊疗卡卡号
     * @return
     */
    @Override
    public MedicalCard getById(String medicalCardNo) {
        return medicalCardRepo.findByMedicalCardNo(medicalCardNo).orElseThrow(() -> new AppException("未查找到指定诊疗卡信息"));
    }

    /**
     * 查询所有诊疗卡,结果进行分页
     *
     * @param medicalCard
     * @param pager 分页参数
     * @return
     */
    @Override
    public PageImpl<MedicalCard> getAll(MedicalCard medicalCard, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(),pager.getLimit(),Sort.by(Sort.Direction.DESC,"createTime"));
        Page<MedicalCard> drugs = medicalCardRepo.findAll(SpecificationBuilder.toSpecification(medicalCard),pageable);
        return new PageImpl<>(drugs.getContent(),pageable,drugs.getTotalElements());
    }

    /**
     * 删除指定诊疗卡信息
     *
     * @param id 指定诊疗卡id
     * @return
     */
    @Override
    public String delete(Integer id) {
        medicalCardRepo.delete(medicalCardRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定诊疗卡信息")));
        return DELETE_SUCCESS;
    }

    /**
     * 删除指定诊疗卡卡号的诊疗卡信息
     *
     * @param medicalCardNo 指定诊疗卡卡号
     * @return
     */
    @Override
    public String delete(String medicalCardNo) {
        medicalCardRepo.delete(medicalCardRepo.findByMedicalCardNo(medicalCardNo).orElseThrow(() -> new AppException("未查找到指定诊疗卡信息")));
        return DELETE_SUCCESS;
    }

    /**
     * 新增或更新诊疗卡信息
     *
     * @param medicalCard 待新增或更新的诊疗卡信息
     * @return
     */
    @Override
    public MedicalCard save(MedicalCard medicalCard) {
        Optional<MedicalCard> optionalMedicalCard = medicalCardRepo.findByMedicalCardNo(medicalCard.getMedicalCardNo());
        if (optionalMedicalCard.isPresent()) {
            // 更新
            return medicalCardRepo.save(optionalMedicalCard.get().update(medicalCard));
        } else {
            // 新增
            return medicalCardRepo.save(medicalCard);
        }
    }

}
