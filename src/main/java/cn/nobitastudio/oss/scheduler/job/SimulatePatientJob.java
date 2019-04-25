package cn.nobitastudio.oss.scheduler.job;

import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.oss.entity.*;
import cn.nobitastudio.oss.helper.SmsHelper;
import cn.nobitastudio.oss.model.enumeration.ElectronicCaseType;
import cn.nobitastudio.oss.model.enumeration.ItemType;
import cn.nobitastudio.oss.model.enumeration.OrderState;
import cn.nobitastudio.oss.model.error.ErrorCode;
import cn.nobitastudio.oss.model.vo.SmsSendResult;
import cn.nobitastudio.oss.repo.*;
import cn.nobitastudio.oss.util.CommonUtil;
import cn.nobitastudio.oss.util.SpringBeanUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static cn.nobitastudio.oss.helper.SmsHelper.ORDER_ID;
import static cn.nobitastudio.oss.helper.SmsHelper.REGISTRATON_RECORD_ID;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/04 14:48
 * @description 模拟病人就诊，想订单中插入医嘱，药品，检查等信息
 */
public class SimulatePatientJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        OSSOrderRepo ossOrderRepo = SpringBeanUtil.getBean(OSSOrderRepo.class);
        RegistrationRecordRepo registrationRecordRepo = SpringBeanUtil.getBean(RegistrationRecordRepo.class);
        ContainRepo containRepo = SpringBeanUtil.getBean(ContainRepo.class);
        ElectronicCaseRepo electronicCaseRepo = SpringBeanUtil.getBean(ElectronicCaseRepo.class);
        DrugRepo drugRepo = SpringBeanUtil.getBean(DrugRepo.class);
        CheckItemRepo checkItemRepo = SpringBeanUtil.getBean(CheckItemRepo.class);
        OperationItemRepo operationItemRepo = SpringBeanUtil.getBean(OperationItemRepo.class);

        RegistrationRecord registrationRecord = registrationRecordRepo.findById(jobDataMap.getString(REGISTRATON_RECORD_ID))
                .orElseThrow(() -> new AppException("未查找到指定挂号单", ErrorCode.NOT_FIND_REGISTRATION_BY_ID));

        double allCost = 0;
        // 加入药品信息
        List<Drug> allDrugs = drugRepo.findAll(null);
        List<Drug> selectedDrugs = new ArrayList<>();  // 选择的药品种类
        List<Integer> drugCount = new ArrayList<>(); // 对应的数量
        if (allDrugs.size() > 0) {
            int drugTypeCount = CommonUtil.getRandom(1, Math.min(allDrugs.size(), 10)); // 单次药品的数量
            while (selectedDrugs.size() != drugTypeCount) {
                Drug drug = allDrugs.get(CommonUtil.getRandom(0, allDrugs.size() - 1));
                if (!selectedDrugs.contains(drug)) {
                    selectedDrugs.add(drug);
                    int count = CommonUtil.getRandom(1, 20);
                    drugCount.add(count);
                    allCost += allCost + drug.getPrice() * count; // 更新新订单总价
                }
            }
        }

        // 加入检查信息
        List<CheckItem> allCheckItem = checkItemRepo.findAll(null);
        List<CheckItem> selectedCheckItems = new ArrayList<>();  // 选择检查项种类
        List<Integer> checkItemCount = new ArrayList<>(); // 对应的数量
        if (allCheckItem.size() > 0) {
            int checkTypeCount = CommonUtil.getRandom(1, Math.min(allCheckItem.size(), 5)); // 单次检查的项目数量
            while (selectedCheckItems.size() != checkTypeCount) {
                CheckItem checkItem = allCheckItem.get(CommonUtil.getRandom(0, allCheckItem.size() - 1));
                if (!selectedCheckItems.contains(checkItem)) {
                    selectedCheckItems.add(checkItem);
                    int count = CommonUtil.getRandom(1, 5);
                    checkItemCount.add(count);
                    allCost += allCost + checkItem.getPrice() * count; // 更新新订单总价
                }
            }
        }

        // 加入手术信息
        List<OperationItem> allOperationItem = operationItemRepo.findAll(null);
        List<OperationItem> selectedOperationItems = new ArrayList<>();  // 选择手术项种类
        List<Integer> operationCount = new ArrayList<>(); // 对应的数量
        if (allOperationItem.size() > 0) {
            int operationTypeCount = CommonUtil.getRandom(1, Math.min(allOperationItem.size(), 3)); // 单次检查的项目数量
            while (selectedOperationItems.size() != operationTypeCount) {
                OperationItem operationItem = allOperationItem.get(CommonUtil.getRandom(0, allOperationItem.size() - 1));
                if (!selectedOperationItems.contains(operationItem)) {
                    selectedOperationItems.add(operationItem);
                    int count = CommonUtil.getRandom(1, 3);
                    operationCount.add(count);
                    allCost += allCost + operationItem.getPrice() * count; // 更新新订单总价
                }
            }
        }

        OSSOrder ossOrder = ossOrderRepo.save(new OSSOrder(registrationRecord.getMedicalCardId(), allCost));   // 新建订单,用于保存对应的检查信息等等


        // 向 订单中新增对应的 contain中新增  医生医嘱，用药信息,检查信息，等
        ElectronicCase electronicCase = electronicCaseRepo.save(new ElectronicCase(ossOrder.getId(), registrationRecord.getId(), LocalDateTime.now(), null,
                "这是诊断描述这是诊断描述这是诊断描述这是诊断描述这是诊断描述这是诊断描述这是诊断描述这是诊断描述",
                "这是诊断医嘱这是诊断医嘱这是诊断医嘱这是诊断医嘱这是诊断医嘱这是诊断医嘱这是诊断医嘱这是诊断医嘱",
                "这是用药医嘱这是用药医嘱这是用药医嘱这是用药医嘱这是用药医嘱这是用药医嘱这是用药医嘱这是用药医嘱",
                "这是检查医嘱这是检查医嘱这是检查医嘱这是检查医嘱这是检查医嘱这是检查医嘱这是检查医嘱这是检查医嘱",
                "这是其他医嘱这是其他医嘱这是其他医嘱这是其他医嘱这是其他医嘱这是其他医嘱这是其他医嘱这是其他医嘱",
                ElectronicCaseType.getFromPosition(CommonUtil.getRandom(0, 2)))); // 保存该病例信息

        // 保存 订单 - 包含 - 药品/检查项/手术项
        for (int i = 0; i < selectedDrugs.size(); i++) {
            containRepo.save(new Contain(ossOrder.getId(), ItemType.DRUG,
                    selectedDrugs.get(i).getId().toString(), drugCount.get(i)));
        }
        // 保存 订单 - 包含 - 检查项
        for (int i = 0; i < selectedCheckItems.size(); i++) {
            containRepo.save(new Contain(ossOrder.getId(), ItemType.CHECK,
                    selectedCheckItems.get(i).getId().toString(), checkItemCount.get(i)));
        }
        // 保存 订单 - 包含 - 手术项
        for (int i = 0; i < selectedOperationItems.size(); i++) {
            containRepo.save(new Contain(ossOrder.getId(), ItemType.OPERATION,
                    selectedOperationItems.get(i).getId().toString(), operationCount.get(i)));
        }
    }

}
