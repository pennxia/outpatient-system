package cn.nobitastudio.oss.scheduler.job;

import cn.nobitastudio.oss.entity.OSSOrder;
import cn.nobitastudio.oss.model.enumeration.OrderState;
import cn.nobitastudio.oss.repo.OSSOrderRepo;
import cn.nobitastudio.oss.helper.SmsHelper;
import cn.nobitastudio.oss.model.vo.SmsSendResult;
import cn.nobitastudio.oss.util.SpringBeanUtil;
import org.quartz.*;

import static cn.nobitastudio.oss.helper.SmsHelper.*;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/04 14:48
 * @description
 */
public class RemindJob implements Job {

    // todo 可以写成application.yml 中的配置项
    public static final Integer AHEAD_HOUR_TIME = 2;  // 默认提前2小时进行提醒

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        OSSOrder ossOrder = SpringBeanUtil.getBean(OSSOrderRepo.class).findById(jobDataMap.getString(ORDER_ID)).orElseThrow(() -> new JobExecutionException("未查找到指定订单"));
        SmsHelper smsHelper = SpringBeanUtil.getBean(SmsHelper.class);
        if (ossOrder.getState().equals(OrderState.HAVE_PAY)) {
            SmsSendResult smsSendResult = smsHelper.sendSms(smsHelper.castJobDataMapToMap(jobDataMap));
        }
    }

}
