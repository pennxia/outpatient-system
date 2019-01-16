package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.CollectDoctor;
import cn.nobitastudio.oss.model.vo.DoctorAndDepartment;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/04 17:42
 * @description
 */
public interface CollectDoctorService {
    /**
     * 查询指定id 收藏关系
     *
     * @param id 指定收藏关系id
     * @return
     */
    CollectDoctor getById(Integer id);

    /**
     * 查询所有收藏关系,结果进行分页
     *
     * @param pager 分页参数
     * @return
     */
    PageImpl<CollectDoctor> getAll(CollectDoctor collectDoctor, Pager pager);

    /**
     * 删除指定id收藏关系
     *
     * @param id 指定收藏关系id
     * @return
     */
    String delete(Integer id);

    /**
     * 新增或更新收藏信息
     *
     * @param collectDoctor 待新增或更新的收藏信息
     * @return
     */
    CollectDoctor save(CollectDoctor collectDoctor);

    /**
     * 用户收藏医生
     * @param collectDoctor
     * @return
     */
    CollectDoctor collect(CollectDoctor collectDoctor);

    /**
     * 用户取消收藏医生
     * @param collectDoctor 待取消收藏参数
     * @return
     */
    String unCollect(CollectDoctor collectDoctor);

    /**
     * 查询指定用户的收藏医生及其医生所对应的科室信息
     * @return
     */
    List<DoctorAndDepartment> getDoctorAndDepartments(Integer userId);
}
