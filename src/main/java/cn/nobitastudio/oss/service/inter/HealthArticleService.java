package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.HealthArticle;
import org.springframework.data.domain.PageImpl;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 18:04
 * @description
 */
public interface HealthArticleService {

    /**
     * 查询指定id健康资讯信息
     * @param id 健康资讯id
     * @return
     */
    HealthArticle getById(Integer id);

    /**
     * 查询所有健康资讯,结果进行分页
     * @param pager 分页参数
     * @return
     */
    PageImpl<HealthArticle> getAll(HealthArticle healthArticle, Pager pager);

    /**
     * 删除指定健康资讯信息
     * @param id 指定健康资讯id
     * @return
     */
    String delete(Integer id);

    /**
     * 新增或更新健康资讯信息
     * @param healthArticle 待新增或更新的健康资讯信息
     * @return
     */
    HealthArticle save(HealthArticle healthArticle);
}
