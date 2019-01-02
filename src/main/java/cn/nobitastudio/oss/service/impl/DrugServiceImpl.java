package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Drug;
import cn.nobitastudio.oss.repo.DrugRepo;
import cn.nobitastudio.oss.service.inter.DrugService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:00
 * @description
 */
@Service
public class DrugServiceImpl implements DrugService {

    private static final String DELETE_SUCCESS = "药品信息删除成功";
    private static final String SAVE_OR_UPDATE_SUCCESS = "药品信息添加或修改成功";

    @Inject
    private DrugRepo drugRepo;

    /**
     * 查询指定id药品信息
     *
     * @param id 指定药品id
     * @return
     */
    @Override
    public Drug getById(Integer id) {
        return drugRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定药品信息"));
    }

    /**
     * 查询所有药品,结果进行分页
     *
     * @param drug
     * @param pager 分页参数
     * @return
     */
    @Override
    public PageImpl<Drug> getAll(Drug drug, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(),pager.getLimit(),Sort.by(Sort.Direction.ASC,"id"));
        Page<Drug> drugs = drugRepo.findAll(SpecificationBuilder.toSpecification(drug),pageable);
        return new PageImpl<>(drugs.getContent(),pageable,drugs.getTotalElements());
    }

    /**
     * 删除指定药品信息
     *
     * @param id 指定药品id
     * @return
     */
    @Override
    public String delete(Integer id) {
        drugRepo.delete(drugRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定药品信息")));
        return DELETE_SUCCESS;
    }

    /**
     * 新增或更新药品信息
     *
     * @param drug@return
     */
    @Override
    public Drug save(Drug drug) {
        return drugRepo.save(drug);
    }
}
