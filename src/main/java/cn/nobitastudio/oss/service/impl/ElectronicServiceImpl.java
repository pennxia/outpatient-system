package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.ElectronicCase;
import cn.nobitastudio.oss.model.dto.StandardInfo;
import cn.nobitastudio.oss.model.error.ErrorCode;
import cn.nobitastudio.oss.repo.ElectronicCaseRepo;
import cn.nobitastudio.oss.service.inter.ElectronicService;
import org.springframework.data.domain.*;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/25 16:17
 * @description
 */
public class ElectronicServiceImpl implements ElectronicService {

    @Inject
    private ElectronicCaseRepo electronicCaseRepo;

    /**
     * 查询指定id的电子病历
     *
     * @param id 指定绑定关系id
     * @return
     */
    @Override
    public ElectronicCase getById(Integer id) {
        return electronicCaseRepo.findById(id).orElseThrow(() -> new AppException("未查找指定电子病历", ErrorCode.NOT_FIND_ELECTRONIC_CASE_BY_ID));
    }

    /**
     * 查询所有电子病历
     *
     * @param electronicCase
     * @param pager          分页参数
     * @return
     */
    @Override
    public PageImpl<ElectronicCase> getAll(ElectronicCase electronicCase, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(), pager.getLimit(), Sort.by(Sort.Direction.DESC, "createTime"));
        Page<ElectronicCase> electronicCases = electronicCaseRepo.findAll(SpecificationBuilder.toSpecification(electronicCase), pageable);
        return new PageImpl<>(electronicCases.getContent(), pageable, electronicCases.getTotalElements());
    }

    /**
     * 删除指定id电子病历
     *
     * @param id
     * @return
     */
    @Override
    public StandardInfo delete(Integer id) {
        electronicCaseRepo.delete(electronicCaseRepo.findById(id).orElseThrow(() -> new AppException("未查找指定电子病历", ErrorCode.NOT_FIND_ELECTRONIC_CASE_BY_ID)));
        return new StandardInfo("删除成功");
    }

    /**
     * 新增或更新电子病历信息
     *
     * @param electronicCase
     * @return
     */
    @Override
    public ElectronicCase save(ElectronicCase electronicCase) {
        return electronicCaseRepo.save(electronicCase);
    }
}
