package cn.nobitastudio.oss.scheduler.job;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.oss.entity.OSSOrder;
import cn.nobitastudio.oss.model.enumeration.OrderState;
import cn.nobitastudio.oss.repo.OSSOrderRepo;
import cn.nobitastudio.oss.util.SpringBeanUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/12 15:40
 * @description 用于在挂号成功后检查订单状态
 */
public class CheckOrderStateJob implements Job {

    // todo 可以写成application.yml 中的配置项
    public static final Integer LEFT_PAY_TIME = 30;  // 默认支付时间为30分钟;
    public static final String OSS_ORDER = "ossOrder";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        OSSOrder ossOrder = (OSSOrder) jobExecutionContext.getJobDetail().getJobDataMap().get(OSS_ORDER);
        OSSOrderRepo ossOrderRepo = SpringBeanUtil.getBean(OSSOrderRepo.class);
        OSSOrder newOssOrder = ossOrderRepo.findById(ossOrder.getId()).orElseThrow(() -> new AppException("订单被删除,未查找到指定订单"));
        if (newOssOrder.getState().equals(OrderState.WAITING_PAY)) {
            // 更改状态位   OrderState.AUTO_CANCEL_PAY
            newOssOrder.setState(OrderState.AUTO_CANCEL_PAY);
            // 设置取消时间
            newOssOrder.setCancelTime(LocalDateTime.now());
            ossOrderRepo.save(newOssOrder);
        }
    }
}
