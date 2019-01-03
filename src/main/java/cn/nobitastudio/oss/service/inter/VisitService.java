package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Visit;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 12:56
 * @description
 */
public interface VisitService {

    /**
     * 查询指定id号源信息
     *
     * @param id 号源id
     * @return
     */
    Visit getById(Integer id);

    /**
     * 查询所有号源,结果进行分页
     *
     * @param pager 分页参数
     * @return
     */
    PageImpl<Visit> getAll(Visit visit, Pager pager);

    /**
     * 删除指定号源信息
     *
     * @param id 指定号源信息id
     * @return
     */
    String delete(Integer id);

    /**
     * 新增或更新号源信息
     *
     * @param visit 待新增或更新的号源信息
     * @return
     */
    Visit save(Visit visit);

    /**
     * 添加指定数量到指定号源中（即新增left_Amount，并且更新Amount）
     *
     * @param id
     * @return
     */
    @Transactional
    Visit plus(Integer id, Integer count);

    /**
     * 减少指定号源数量（即，减少left_amount,不更新Amount）
     * @param id
     * @param count
     * @return
     */
    @Transactional
    Visit minus(Integer id,Integer count);
}
