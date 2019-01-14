package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.*;
import cn.nobitastudio.oss.model.dto.ConfirmRegisterDTO;
import cn.nobitastudio.oss.model.dto.RegisterDTO;
import cn.nobitastudio.oss.model.enumeration.*;
import cn.nobitastudio.oss.model.vo.ConfirmOrCancelRegisterVO;
import cn.nobitastudio.oss.model.vo.SmsSendResult;
import cn.nobitastudio.oss.repo.*;
import cn.nobitastudio.oss.helper.QuartzHelper;
import cn.nobitastudio.oss.service.inter.RegistrationRecordService;
import cn.nobitastudio.oss.helper.SmsHelper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/06 15:13
 * @description
 */
@Service
public class RegistrationRecordServiceImpl implements RegistrationRecordService {

    static Logger logger = LoggerFactory.getLogger(RegistrationRecordServiceImpl.class);

    private static final String DELETE_SUCCESS = "挂号记录信息删除成功";
    private static final String SAVE_OR_UPDATE_SUCCESS = "挂号记录信息添加或修改成功";

    @Inject
    private Scheduler scheduler;
    @Inject
    private SmsHelper smsHelper;
    @Inject
    private QuartzHelper quartzHelper;
    @Inject
    private RegistrationRecordRepo registrationRecordRepo;
    @Inject
    private VisitRepo visitRepo;
    @Inject
    private DoctorRepo doctorRepo;
    @Inject
    private DepartmentRepo departmentRepo;
    @Inject
    private UserRepo userRepo;
    @Inject
    private MedicalCardRepo medicalCardRepo;
    @Inject
    private OSSOrderRepo ossOrderRepo;
    @Inject
    private DiagnosisRoomRepo diagnosisRoomRepo;
    @Inject
    private ContainRepo containRepo;
    @Inject
    private ExecutorService executorService;  //  cacheThreadPool

    /**
     * 查询指定id挂号记录信息
     *
     * @param id 挂号记录id
     * @return
     */
    @Override
    public RegistrationRecord getById(String id) {
        return registrationRecordRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定id的挂号记录"));
    }

    /**
     * 查询所有挂号记录,结果进行分页
     *
     * @param registrationRecord
     * @param pager              分页参数
     * @return
     */
    @Override
    public PageImpl<RegistrationRecord> getAll(RegistrationRecord registrationRecord, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(), pager.getLimit(), Sort.by(Sort.Direction.DESC, "createTime"));
        Page<RegistrationRecord> registrationRecords = registrationRecordRepo.findAll(SpecificationBuilder.toSpecification(registrationRecord), pageable);
        return new PageImpl<>(registrationRecords.getContent(), pageable, registrationRecords.getTotalElements());
    }

    /**
     * 删除指定挂号记录信息
     *
     * @param id 指定挂号记录id
     * @return
     */
    @Override
    public String delete(String id) {
        registrationRecordRepo.delete(registrationRecordRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定id的挂号记录")));
        return DELETE_SUCCESS;
    }

    /**
     * 新增或更新挂号记录
     *
     * @param registrationRecord 待新增或更新的挂号记录
     * @return
     */
    @Override
    public RegistrationRecord save(RegistrationRecord registrationRecord) {
        return registrationRecordRepo.save(registrationRecord);
    }

    /**
     * 用户进行挂号操作
     * 产生一个挂号单 以及 一个待支付的订单
     * 强制串行执行
     *
     * @param registerDTO
     * @return
     */
    @Transactional
    @Override
    public synchronized RegistrationRecord register(RegisterDTO registerDTO) {
        // 待实现
        Visit visit = visitRepo.findById(registerDTO.getVisitId()).orElseThrow(() -> new AppException("未查询到指定号源信息"));
        if (visit.getLeftAmount().equals(0)) {
            throw new AppException("挂号失败,该号源已全部挂完");
        }
        User user = userRepo.findById(registerDTO.getUserId()).orElseThrow(() -> new AppException("未查找到指定用户"));
        MedicalCard medicalCard = medicalCardRepo.findById(registerDTO.getMedicalCardId()).orElseThrow(() -> new AppException("未查找到指定诊疗卡"));
        Doctor doctor = doctorRepo.findById(visit.getDoctorId()).orElseThrow(() -> new AppException("未查找到指定医生信息"));
        Department department = departmentRepo.findById(doctor.getDepartmentId()).orElseThrow(() -> new AppException("未查找到指定科室信息"));
        DiagnosisRoom diagnosisRoom = diagnosisRoomRepo.findById(visit.getDiagnosisRoomId()).orElseThrow(() -> new AppException("未查询到指定诊疗室"));
        // 检测是否已经成功挂了该号或者存在等待支付的订单.
        checkWhetherRegister(medicalCard, visit);
        // 生成 挂号单单号,挂号单id,并保存 现在只支持 ANDRIOD 客户端进行挂号
        Integer diagnosisNo = visit.getAmount() - visit.getLeftAmount() + 1; // 就诊序号
        RegistrationRecord registrationRecord = new RegistrationRecord(Channel.OSS_ANDROID_APP, user.getId(), visit.getId(), medicalCard.getId(), diagnosisNo);
        registrationRecordRepo.save(registrationRecord);
        // visit 资源减少1
        visit.setLeftAmount(visit.getLeftAmount() - 1);
        visitRepo.save(visit);  // 可能不需要此操作,保险起见
        // 生成 订单信息
        OSSOrder ossOrder = new OSSOrder(ItemType.REGISTRATION_RECORD, user.getId(), medicalCard.getId());
        ossOrderRepo.save(ossOrder);
        // 生成订单和挂号单之间的绑定关系
        Contain contain = new Contain(ossOrder.getId(), ItemType.REGISTRATION_RECORD, registrationRecord.getId());
        containRepo.save(contain);
        // 挂号成功等待支付,直接发送通知短信.
        executorService.execute(() -> {
            // todo 如果短信发送失败,尝试重新发送. 暂时未实现
            SmsSendResult smsSendResult = smsHelper.sendSms(smsHelper.initRegisterSuccessOrDiagnosisRemindSms(user, medicalCard, doctor, department, visit, diagnosisRoom, diagnosisNo, SmsMessageType.REGISTER_SUCCESS_WAITING_PAY));
        });
        // 生成订单是否支付检测,30分钟后发现未支付则更新订单状态状态为AUTO_CANCEL_PAY,检测若该订单还未支付,则改变支付状态位自动取消. 可能需要将其让如主线程执行
        executorService.execute(() -> createCheckOrderStateQuartzPlan(ossOrder));
        return registrationRecord;
    }

    /**
     * 用户支付完成.确认还挂号单以及对应的订单.
     *
     * @param confirmRegisterDTO
     * @return
     */
    @Override
    public ConfirmOrCancelRegisterVO confirmRegister(ConfirmRegisterDTO confirmRegisterDTO) {
        RegistrationRecord registrationRecord = registrationRecordRepo.findById(confirmRegisterDTO.getRegistrationRecordId()).orElseThrow(() -> new AppException("未查找到指定的挂号"));
        OSSOrder ossOrder = ossOrderRepo.findByRegistrationId(confirmRegisterDTO.getRegistrationRecordId()).orElseThrow(() -> new AppException("未查找到该挂号单对应的订单"));
        Visit visit = visitRepo.findById(registrationRecord.getVisitId()).orElseThrow(() -> new AppException("未查询到指定号源信息"));
        User user = userRepo.findById(ossOrder.getUserId()).orElseThrow(() -> new AppException("未查找到指定用户"));
        MedicalCard medicalCard = medicalCardRepo.findById(registrationRecord.getMedicalCardId()).orElseThrow(() -> new AppException("未查找到指定诊疗卡"));
        Doctor doctor = doctorRepo.findById(visit.getDoctorId()).orElseThrow(() -> new AppException("未查找到指定医生信息"));
        Department department = departmentRepo.findById(doctor.getDepartmentId()).orElseThrow(() -> new AppException("未查找到指定科室信息"));
        DiagnosisRoom diagnosisRoom = diagnosisRoomRepo.findById(visit.getDiagnosisRoomId()).orElseThrow(() -> new AppException("未查询到指定诊疗室"));
        // 更改订单状态
        ossOrder.setState(OrderState.HAVE_PAY);
        // 设置支付渠道
        ossOrder.setPaymentChannel(confirmRegisterDTO.getPaymentChannel());
        // 设置支付时间
        ossOrder.setPayTime(LocalDateTime.now());
        ossOrderRepo.save(ossOrder);
        // 支付成功后
        // 通过JPush 发送Android 客户端的通知.
        executorService.execute(() -> {
            // todo sendJPushNotification 通过JPush发送通知.
            sendRegisterSuccessHavePayNotificationByJPush(user);
        });
        // 储存未来就医提醒的调度计划
        executorService.execute(() -> {
            createDiagnosisRemindQuartzPlan(ossOrder, visit, user, medicalCard, doctor, department, diagnosisRoom, registrationRecord.getDiagnosisNo());
        });
        // 发送支付成功
        executorService.execute(() -> {
            // todo 发送失败时尝试重新发送
            SmsSendResult smsSendResult = smsHelper.sendSms(smsHelper.initRegisterSuccessOrDiagnosisRemindSms(user, medicalCard, doctor, department, visit, diagnosisRoom, registrationRecord.getDiagnosisNo(), SmsMessageType.REGISTER_SUCCESS_HAVE_PAY));
        });
        // 取消 checkOrderStateJob 的quartz检测计划
        executorService.execute(() -> {
            TriggerKey triggerKey = quartzHelper.createCheckOrderStateTriggerKey(ossOrder);
            try {
                scheduler.unscheduleJob(triggerKey);
            } catch (SchedulerException e) {
                // todo 取消失败了，应当尝试重新取消,虽然CheckOrderStateJob做了兼容处理，即便是取消失败也没事.但最好是取消调度计划
                logger.info("取消未来就诊提醒quartz计划失败,triggerKey:" + triggerKey);
            }
        });
        return new ConfirmOrCancelRegisterVO(ossOrder, registrationRecord);
    }

    /**
     * 用户取消预约该挂号单. 分为两种情况: 1.还未支付便取消, 2.已支付再取消
     *
     * @param id
     * @return
     */
    @Override
    public ConfirmOrCancelRegisterVO cancelRegister(String id) {
        RegistrationRecord registrationRecord = registrationRecordRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定的挂号"));
        OSSOrder ossOrder = ossOrderRepo.findByRegistrationId(id).orElseThrow(() -> new AppException("未查找到该挂号单对应的订单"));
        Visit visit = visitRepo.findById(registrationRecord.getVisitId()).orElseThrow(() -> new AppException("未查询到指定号源信息"));
        User user = userRepo.findById(ossOrder.getUserId()).orElseThrow(() -> new AppException("未查找到指定用户"));
        MedicalCard medicalCard = medicalCardRepo.findById(registrationRecord.getMedicalCardId()).orElseThrow(() -> new AppException("未查找到指定诊疗卡"));
        if (ossOrder.getState().equals(OrderState.HAVE_PAY) || ossOrder.getState().equals(OrderState.WAITING_PAY)) {
            if (ossOrder.getState().equals(OrderState.HAVE_PAY)) {
                // 已经支付的情况下
                // 取消未来就诊提醒quartz计划
                executorService.execute(() -> {
                    TriggerKey triggerKey = quartzHelper.createDiagnosisRemindTriggerKey(user, medicalCard, RemindType.DIAGNOSIS_REMIND, visit, registrationRecord.getDiagnosisNo());
                    try {
                        scheduler.unscheduleJob(triggerKey);
                    } catch (SchedulerException e) {
                        // todo 取消失败了，应当尝试重新取消,虽然RemindJob做了兼容处理，即便是取消失败也没事.但最好是取消调度计划
                        logger.info("取消未来就诊提醒quartz计划失败,triggerKey:" + triggerKey);
                    }
                });
                // todo 如果是在线支付方式,进行退款操作
                if (!ossOrder.getPaymentChannel().equals(PaymentChannel.HOSPITAL_MEDICAL_CAR) && !ossOrder.getPaymentChannel().equals(PaymentChannel.HOSPITAL_MONEY)) {
                    // 退款
                    executorService.execute(() -> {

                    });
                }
            }
            // 更新订单状态  必须在取消未来就诊提醒quartz计划之后
            ossOrder.setState(OrderState.CANCEL_PAY);
            ossOrder.setCancelTime(LocalDateTime.now());
            ossOrderRepo.save(ossOrder);
        } else {
            throw new AppException("该挂号单已处于取消预约状态,请勿重复取消");
        }
        return new ConfirmOrCancelRegisterVO(ossOrder, registrationRecord);
    }

    /**
     * 检查订单的支付状态
     *
     * @param ossOrder
     */
    private void createCheckOrderStateQuartzPlan(OSSOrder ossOrder) {
        JobDetail checkOrderStateJob = quartzHelper.newCheckOrderStateJob(ossOrder);
        Trigger checkOrderStateTrigger = quartzHelper.newCheckOrderStateTrigger(ossOrder);
        try {
            scheduler.scheduleJob(checkOrderStateJob, checkOrderStateTrigger);
        } catch (SchedulerException e) {
            logger.info("订单支付状态检查调度计划调度失败.jobDetail:" + checkOrderStateJob + ".trigger:" + checkOrderStateTrigger);
            e.printStackTrace();
        }
    }

    /**
     * 通过Jpush 发送通知信息
     */
    private void sendRegisterSuccessHavePayNotificationByJPush(User user) {

    }

    /**
     * 存储未来提醒的quartz 计划.
     *
     * @param visit
     * @param user
     * @param medicalCard
     * @param doctor
     * @param department
     * @param diagnosisRoom
     * @param diagnosisNo
     * @throws SchedulerException
     */
    private void createDiagnosisRemindQuartzPlan(OSSOrder ossOrder, Visit visit, User user, MedicalCard medicalCard, Doctor doctor, Department department, DiagnosisRoom diagnosisRoom, Integer diagnosisNo) {
        JobDetail registerJobDetail = quartzHelper.newDiagnosisRemindJobDetailInstance(ossOrder, visit, user, department, medicalCard, doctor, diagnosisRoom, diagnosisNo);
        Trigger trigger = quartzHelper.newDiagnosisRemindTriggerInstance(visit, user, medicalCard, diagnosisNo);
        try {
            scheduler.scheduleJob(registerJobDetail, trigger);
        } catch (SchedulerException e) {
            // todo 调度计划失败,尝试重新调度
            logger.info("就诊提醒调度计划调度失败.jobDetail:" + registerJobDetail + ".trigger:" + trigger);
            e.printStackTrace();
        }
    }

    /**
     * 检查用户是否已经挂此号
     *
     * @param medicalCard
     * @param visit
     */
    private void checkWhetherRegister(MedicalCard medicalCard, Visit visit) {
        List<RegistrationRecord> registrationRecords = registrationRecordRepo.findByMedicalCardIdAndVisitId(medicalCard.getId(), visit.getId());
        if (registrationRecords.size() > 0) {
            // 挂过此号
            for (RegistrationRecord registrationRecord : registrationRecords) {
                // 关联对应的订单.如果存在支付成功或者等待支付.则挂号失败.
                OSSOrder ossOrder = ossOrderRepo.findByRegistrationId(registrationRecord.getId()).orElseThrow(() -> new AppException("未查找该挂号单对应的订单信息"));
                if (ossOrder.getState().equals(OrderState.WAITING_PAY) || ossOrder.getState().equals(OrderState.HAVE_PAY)) {
                    throw new AppException("已挂该号,请勿重复挂号");
                }
            }
        }
    }
}
