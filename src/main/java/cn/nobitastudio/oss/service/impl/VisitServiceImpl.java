package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Visit;
import cn.nobitastudio.oss.repo.VisitRepo;
import cn.nobitastudio.oss.service.inter.VisitService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 12:56
 * @description
 */
@Service
public class VisitServiceImpl implements VisitService {

    private static final String DELETE_SUCCESS = "号源信息删除成功";
    private static final String SAVE_OR_UPDATE_SUCCESS = "号源信息添加或修改成功";

    @Inject
    private VisitRepo visitRepo;

    /**
     * 查询指定id号源信息
     *
     * @param id 号源id
     * @return
     */
    @Override
    public Visit getById(Integer id) {
        return visitRepo.findById(id).orElseThrow(() -> new AppException("未查找到相关号源信息"));
    }

    /**
     * 查询所有号源,结果进行分页
     *
     * @param visit
     * @param pager 分页参数
     * @return
     */
    @Override
    public PageImpl<Visit> getAll(Visit visit, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(),pager.getLimit(),Sort.by(Sort.Direction.DESC,"diagnosisTime"));
        Page<Visit> visits = visitRepo.findAll(SpecificationBuilder.toSpecification(visit),pageable);
        return new PageImpl<>(visits.getContent(),pageable,visits.getTotalElements());
    }

    /**
     * 删除指定号源信息
     *
     * @param id 指定号源信息id
     * @return
     */
    @Override
    public String delete(Integer id) {
        visitRepo.delete(visitRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定号源信息")));
        return DELETE_SUCCESS;
    }

    /**
     * 新增或更新号源信息
     *
     * @param visit 待新增或更新的号源信息
     * @return
     */
    @Override
    public Visit save(Visit visit) {
        return visitRepo.save(visit);
    }

    /**
     * 添加指定数量到指定号源中（即新增left_Amount，并且更新Amount）
     *
     * @param id
     * @param count
     * @return
     */
    @Transactional
    @Override
    public Visit plus(Integer id, Integer count) {
        Visit visit = visitRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定号源信息"));
        visit.setAmount(visit.getAmount() + 1);
        visit.setLeftAmount(visit.getLeftAmount() + 1);
        return visitRepo.save(visit);
    }

    /**
     * 减少指定号源数量（即，减少left_amount,不更新Amount）
     *
     * @param id
     * @param count
     * @return
     */
    @Transactional
    @Override
    public Visit minus(Integer id, Integer count) {
        Visit visit = visitRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定号源信息"));
        if (visit.getLeftAmount() < 1) {
            throw new AppException("当前号源已被全部挂完");
        }
        // 存储用户挂号记录
        visit.setLeftAmount(visit.getLeftAmount() - 1);
        return visitRepo.save(visit);
    }
}
