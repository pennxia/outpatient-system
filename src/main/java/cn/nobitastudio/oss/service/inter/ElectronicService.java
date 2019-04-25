package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.ElectronicCase;
import cn.nobitastudio.oss.model.dto.StandardInfo;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 14:50
 * @description 电子病历
 */
public interface ElectronicService {

    /**
     * 查询指定id的电子病历
     *
     * @param id 指定绑定关系id
     * @return
     */
    ElectronicCase getById(Integer id);

    /**
     * 查询所有电子病历
     *
     * @param pager 分页参数
     * @return
     */
    PageImpl<ElectronicCase> getAll(ElectronicCase electronicCase, Pager pager);

    /**
     * 删除指定id电子病历
     *
     * @param id
     * @return
     */
    StandardInfo delete(Integer id);

    /**
     * 新增或更新电子病历信息
     *
     * @param electronicCase
     * @return
     */
    ElectronicCase save(ElectronicCase electronicCase);
}
