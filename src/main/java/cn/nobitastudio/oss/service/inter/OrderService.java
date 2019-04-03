package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.OSSOrder;
import org.springframework.data.domain.PageImpl;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:35
 * @description
 */
public interface OrderService {

    /**
     * 查询指定id订单信息
     *
     * @param id 指定订单id
     * @return
     */
    OSSOrder getById(String id);

    /**
     * 查询所有,结果进行分页
     *
     * @param pager 分页参数
     * @return
     */
    PageImpl<OSSOrder> getAll(OSSOrder OSSOrder, Pager pager);

    /**
     * 查询全部订单信息
     * @return
     */
    List<OSSOrder> getAll();

    /**
     * 删除指定id订单
     *
     * @param id 指定订单id
     * @return
     */
    String delete(String id);

    /**
     * 新增或更新订单信息
     *
     * @param ossOrder 待新增或更新的订单信息
     * @return
     */
    OSSOrder save(OSSOrder ossOrder);

    /**
     * 通过诊疗卡卡号以及号源id 查询订单详情
     * @param medicalCardId
     * @param visitId
     * @return
     */
    OSSOrder getByMedicalCardIdAndVisitId(String medicalCardId,Integer visitId);

    OSSOrder getByIdForPayResult(String id) throws InterruptedException;
}
