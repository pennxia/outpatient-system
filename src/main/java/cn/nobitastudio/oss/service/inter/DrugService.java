package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Drug;
import org.springframework.data.domain.PageImpl;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:00
 * @description
 */
public interface DrugService {
    /**
     * 查询指定id药品信息
     *
     * @param id 指定药品id
     * @return
     */
    Drug getById(Integer id);

    /**
     * 查询所有药品,结果进行分页
     *
     * @param pager 分页参数
     * @return
     */
    PageImpl<Drug> getAll(Drug drug, Pager pager);

    /**
     * 删除指定药品信息
     *
     * @param id 指定药品id
     * @return
     */
    String delete(Integer id);

    /**
     * 新增或更新药品信息
     *
     * @param drug 待新增或更新的药品信息
     * @return
     */
    Drug save(Drug drug);
}
