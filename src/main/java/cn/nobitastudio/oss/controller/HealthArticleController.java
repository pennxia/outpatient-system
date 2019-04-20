package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.criteria.CriteriaException;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.HealthArticle;
import cn.nobitastudio.oss.service.inter.HealthArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 17:57
 * @description
 */
@RestController
@RequestMapping("/health-article")
public class HealthArticleController {

    @Inject
    private HealthArticleService healthArticleService;

    @ApiOperation("查询指定资讯信息")
    @GetMapping("/{id}")
    public ServiceResult<HealthArticle> getById(@PathVariable("id") Integer id) {
        return ServiceResult.success(healthArticleService.getById(id));
    }

    @ApiOperation("查询分页后的健康资讯信息")
    @PostMapping("/query")
    public ServiceResult<PageImpl<HealthArticle>> query(@RequestBody HealthArticle healthArticle, Pager pager) {
        return ServiceResult.success(healthArticleService.getAll(healthArticle, pager));
    }

    @ApiOperation("删除指定健康资讯信息")
    @DeleteMapping("/{id}")
    public ServiceResult<String> deleteById(@PathVariable("id") Integer id) {
        return ServiceResult.success(healthArticleService.delete(id));
    }

    @ApiOperation("保存或更新健康资讯信息")
    @PostMapping
    public ServiceResult<HealthArticle> save(@RequestBody HealthArticle healthArticle) {
        return ServiceResult.success(healthArticleService.save(healthArticle));
    }

    @ApiOperation("查询最新的健康资讯消息，默认最新显示6条各类新闻")
    @GetMapping("/queryLatestArticles")
    public ServiceResult<List<HealthArticle>> queryLatestArticles() {
        return ServiceResult.success(healthArticleService.queryLatestArticles());
    }

    @ApiOperation("进入健康资讯后查看健康资讯.不需查询医院活动,其中顶部轮播图默认显示前5条")
    @GetMapping("/queryMore")
    public ServiceResult<List<HealthArticle>> queryMore(Pager pager) {
        return ServiceResult.success(healthArticleService.queryMore(pager));
    }

}
