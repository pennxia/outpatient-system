package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.MedicalCard;
import cn.nobitastudio.oss.model.dto.CreateMedicalCardDTO;
import org.springframework.data.domain.PageImpl;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:35
 * @description
 */
public interface MedicalCardService {

    /**
     * 查询指定id诊疗卡信息
     * @param id 指定诊疗卡id
     * @return
     */
    MedicalCard getById(String id);

    /**
     * 查询所有诊疗卡,结果进行分页
     * @param pager 分页参数
     * @return
     */
    PageImpl<MedicalCard> getAll(MedicalCard medicalCard, Pager pager);

    /**
     * 删除指定诊疗卡信息
     * @param id 指定诊疗卡id
     * @return
     */
    String delete(String id);

    /**
     * 新增或更新诊疗卡信息,不用于用户创建时调用
     * @param medicalCard 待新增或更新的诊疗卡信息
     * @return
     */
    MedicalCard save(MedicalCard medicalCard);

    /**
     * 更改诊疗卡信息
     * @param medicalCard
     * @return
     */
    MedicalCard modify(MedicalCard medicalCard);
 }
