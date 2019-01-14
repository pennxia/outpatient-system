package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.HealthArticle;
import cn.nobitastudio.oss.repo.HealthArticleRepo;
import cn.nobitastudio.oss.service.inter.HealthArticleService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 18:05
 * @description
 */
@Service
public class HealthArticleServiceImpl implements HealthArticleService {

    private static final String DELETE_SUCCESS = "资讯信息删除成功";
    private static final String SAVE_OR_UPDATE_SUCCESS = "资讯信息添加或修改成功";

    @Inject
    private HealthArticleRepo healthArticleRepo;

    /**
     * 查询指定id健康资讯信息
     *
     * @param id 健康资讯id
     * @return
     */
    @Override
    public HealthArticle getById(Integer id) {
        return healthArticleRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定资讯"));
    }

    /**
     * 查询所有健康资讯,结果进行分页
     *
     * @param healthArticle
     * @param pager         分页参数
     * @return
     */
    @Override
    public PageImpl<HealthArticle> getAll(HealthArticle healthArticle, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(), pager.getLimit(), Sort.by(Sort.Direction.ASC, "publishTime"));
        Page<HealthArticle> healthArticles = healthArticleRepo.findAll(SpecificationBuilder.toSpecification(healthArticle), pageable);
        return new PageImpl<>(healthArticles.getContent(), pageable, healthArticles.getTotalElements());
    }

    /**
     * 删除指定健康资讯信息
     *
     * @param id 指定健康资讯id
     * @return
     */
    @Override
    public String delete(Integer id) {
        healthArticleRepo.delete(healthArticleRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定资讯信息")));
        return DELETE_SUCCESS;
    }

    /**
     * 新增或更新健康资讯信息
     *
     * @param healthArticle 待新增或更新的健康资讯信息
     * @return
     */
    @Override
    public HealthArticle save(HealthArticle healthArticle) {
        return healthArticleRepo.save(healthArticle);
    }
}
