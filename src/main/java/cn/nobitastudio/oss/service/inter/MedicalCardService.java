package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.MedicalCard;
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
    MedicalCard getById(Integer id);

    /**
     * 查询指定诊疗卡号medicalCardNo的诊疗卡信息
     * @param medicalCardNo 指定诊疗卡卡号
     * @return
     */
    MedicalCard getById(String medicalCardNo);

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
    String delete(Integer id);

    /**
     * 删除指定诊疗卡卡号的诊疗卡信息
     * @param medicalCardNo 指定诊疗卡卡号
     * @return
     */
    String delete(String medicalCardNo);

    /**
     * 新增或更新诊疗卡信息
     * @param medicalCard 待新增或更新的诊疗卡信息
     * @return
     */
    MedicalCard save(MedicalCard medicalCard);


}
