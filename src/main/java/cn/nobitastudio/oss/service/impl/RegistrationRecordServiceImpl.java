package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.*;
import cn.nobitastudio.oss.model.dto.RegisterDTO;
import cn.nobitastudio.oss.model.enumeration.Channel;
import cn.nobitastudio.oss.model.enumeration.ItemType;
import cn.nobitastudio.oss.model.enumeration.OrderState;
import cn.nobitastudio.oss.model.vo.SmsSendResult;
import cn.nobitastudio.oss.repo.*;
import cn.nobitastudio.oss.scheduler.util.QuartzUtil;
import cn.nobitastudio.oss.service.inter.RegistrationRecordService;
import cn.nobitastudio.oss.util.DateUtil;
import cn.nobitastudio.oss.util.SmsUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

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
    public synchronized RegistrationRecord register(RegisterDTO registerDTO) throws SchedulerException {
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
        // 生成调度信息,检测若该订单还未支付,则改变支付状态位自动取消.

        // 生成订单和挂号单之间的绑定关系
        Contain contain = new Contain(ossOrder.getId(), ItemType.REGISTRATION_RECORD, registrationRecord.getId());
        containRepo.save(contain);
        // 通过JPush 发送Android 客户端的通知.

        // 挂号成功,直接发送通知短信.
        // todo 如果短信发送失败,尝试重新发送. 暂时未实现
        SmsSendResult smsSendResult = SmsUtil.sendSms(SmsUtil.initRegisterSuccessOrDiagnosisRemindSms(user, medicalCard, doctor, department, visit, diagnosisRoom, diagnosisNo));

        // 储存未来就医提醒的调度计划
        JobDetail registerJobDetail = QuartzUtil.newDiagnosisRemindJobDetailInstance(visit, user, department, medicalCard, doctor, diagnosisRoom, diagnosisNo);
        Trigger trigger = QuartzUtil.newDiagnosisRemindTriggerInstance(visit, user, medicalCard, diagnosisNo);
        scheduler.scheduleJob(registerJobDetail, trigger);
        return registrationRecord;
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
