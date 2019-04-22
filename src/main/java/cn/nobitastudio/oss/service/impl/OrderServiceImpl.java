package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.OSSOrder;
import cn.nobitastudio.oss.entity.OSSOrder;
import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.model.enumeration.OrderState;
import cn.nobitastudio.oss.model.error.ErrorCode;
import cn.nobitastudio.oss.repo.OSSOrderRepo;
import cn.nobitastudio.oss.repo.UserRepo;
import cn.nobitastudio.oss.service.inter.OrderService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 12:39
 * @description 定于订单查询的支持
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final String DELETE_SUCCESS = "订单信息删除成功";
    private static final String SAVE_OR_UPDATE_SUCCESS = "订单信息添加或修改成功";

    @Inject
    private OSSOrderRepo ossOrderRepo;
    @Inject
    private UserRepo userRepo;

    /**
     * 查询指定id订单信息
     *
     * @param id 指定订单id
     * @return
     */
    @Override
    public OSSOrder getById(String id) {
        return ossOrderRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定订单信息", ErrorCode.NOT_FIND_ORDER));
    }

    /**
     * 查询所有
     *
     * @param pager 分页参数
     * @return
     */
    @Override
    public PageImpl<OSSOrder> getAll(OSSOrder ossOrder, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(), pager.getLimit(), Sort.by(Sort.Direction.ASC, "id"));
        Page<OSSOrder> ossOrders = ossOrderRepo.findAll(SpecificationBuilder.toSpecification(ossOrder), pageable);
        return new PageImpl<>(ossOrders.getContent(), pageable, ossOrders.getTotalElements());
    }

    /**
     * 查询全部订单信息
     *
     * @return
     */
    @Override
    public List<OSSOrder> getAll() {
        return ossOrderRepo.findAll(null, Sort.by(Sort.Direction.ASC, "id"));
    }

    /**
     * 删除指定id订单
     *
     * @param id 指定订单id
     * @return
     */
    @Override
    public String delete(String id) {
        ossOrderRepo.delete(ossOrderRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定订单信息")));
        return DELETE_SUCCESS;
    }

    /**
     * 新增或更新订单信息
     *
     * @param ossOrder 待新增或更新的订单信息
     * @return
     */
    @Override
    public OSSOrder save(OSSOrder ossOrder) {
        return ossOrderRepo.save(ossOrder);
    }

    /**
     * 通过诊疗卡卡号以及号源id 查询订单详情
     *
     * @param medicalCardId
     * @param visitId
     * @return
     */
    @Override
    public OSSOrder getByMedicalCardIdAndVisitId(String medicalCardId, Integer visitId) {
        return ossOrderRepo.findByMedicalCardIdAndVisitId(medicalCardId, visitId).get(0);
    }

    @Override
    public OSSOrder getByIdForPayResult(String id) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            OSSOrder ossOrder = ossOrderRepo.findById(id).orElseThrow(
                    () -> new AppException(ErrorCode.get(ErrorCode.NOT_FIND_ORDER), ErrorCode.NOT_FIND_ORDER));
            if (ossOrder.getState().equals(OrderState.HAVE_PAY)) {
                return ossOrder;
            } else {
                Thread.sleep(1000);
            }
        }
        return ossOrderRepo.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.get(ErrorCode.NOT_FIND_ORDER), ErrorCode.NOT_FIND_ORDER));
    }

    /**
     * 查询指定用户的全部订单信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<OSSOrder> getAllOrdersByUserId(Integer userId) {
        userRepo.findById(userId).orElseThrow(() -> new AppException("未查找到指定用户",ErrorCode.NOT_FIND_USER_BY_ID));
        return ossOrderRepo.findByUserIdOrderByCreateTimeDesc(userId);
    }
}
