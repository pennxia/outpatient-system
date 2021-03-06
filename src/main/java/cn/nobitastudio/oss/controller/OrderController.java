package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.criteria.CriteriaException;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.OSSOrder;
import cn.nobitastudio.oss.model.error.ErrorCode;
import cn.nobitastudio.oss.service.inter.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:09
 * @description
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Inject
    private OrderService orderService;

    @ApiOperation("查询指定订单信息")
    @GetMapping("/{id}")
    public ServiceResult<OSSOrder> getById(@PathVariable("id") String id) {
        return ServiceResult.success(orderService.getById(id));
    }

    @ApiOperation("查询指定用户的全部订单信息，分页查询.默认不调用该接口")
    @PostMapping("/query")
    public ServiceResult<PageImpl<OSSOrder>> query(@RequestBody OSSOrder ossOrder, Pager pager) {
        return ServiceResult.success(orderService.getAll(ossOrder, pager));
    }

    @ApiOperation("查询全部订单信息")
    @GetMapping
    public ServiceResult<List<OSSOrder>> query() {
        return ServiceResult.success(orderService.getAll());
    }

    @ApiOperation("删除指定订单")
    @DeleteMapping("/{id}")
    public ServiceResult<String> deleteById(@PathVariable("id") String id) {
        return ServiceResult.success(orderService.delete(id));
    }

    @ApiOperation("保存或更新订单信息")
    @PostMapping
    public ServiceResult<OSSOrder> save(@RequestBody OSSOrder ossOrder) {
        return ServiceResult.success(orderService.save(ossOrder));
    }

    @ApiOperation("通过诊疗卡id.号源id查询订单--设计存在问题，已弃用")
    @GetMapping("/{medicalCardId}/{visitId}")
    public ServiceResult<OSSOrder> getByMedicalCardIdAndVisitId(@PathVariable(name = "medicalCardId") String medicalCardId,
                                                                @PathVariable(name = "visitId") Integer visitId) {
        return ServiceResult.success(orderService.getByMedicalCardIdAndVisitId(medicalCardId, visitId));
    }

    @ApiOperation("支付完成后查询订单状态：仅有两种状态合法:待支付，自动取消(回调超时),此时一直处于等待状态,持续10s")
    @GetMapping("/status/{id}")
    public ServiceResult<OSSOrder> getStatusByIdAfterPay(@PathVariable(name = "id") String id) throws InterruptedException {
        return ServiceResult.success(orderService.getByIdForPayResult(id));
    }

    @ApiOperation("查询指定用户的全部订单")
    @GetMapping("/queryAll/{userId}")
    public ServiceResult<List<OSSOrder>> getAllOrdersByUserId(@PathVariable(name = "userId") Integer userId) {
        return ServiceResult.success(orderService.getAllOrdersByUserId(userId));
    }

}
