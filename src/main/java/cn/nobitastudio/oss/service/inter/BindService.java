package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Bind;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 14:50
 * @description
 */
public interface BindService {

    /**
     * 查询指定id绑定关系
     *
     * @param id 指定绑定关系id
     * @return
     */
    Bind getById(Integer id);

    /**
     * 查询所有绑定关系,结果进行分页
     *
     * @param pager 分页参数
     * @return
     */
    PageImpl<Bind> getAll(Bind bind, Pager pager);

    /**
     * 删除指定id绑定关系
     *
     * @param id 指定绑定关系id
     * @return
     */
    String delete(Integer id);

    /**
     * 新增或更新绑定信息
     *
     * @param bind 待新增或更新的绑定信息
     * @return
     */
    Bind save(Bind bind);

    /**
     * 用户绑定诊疗卡
     * @param bind
     * @return
     */
    @Transactional
    Bind bind(Bind bind);

    /**
     * 用户解绑诊疗卡
     * @param bind 待解绑参数
     * @return
     */
    String unbind(Bind bind);
}
