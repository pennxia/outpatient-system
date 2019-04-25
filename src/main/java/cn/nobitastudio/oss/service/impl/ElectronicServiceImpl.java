package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.*;
import cn.nobitastudio.oss.model.dto.ElectronicCaseDTO;
import cn.nobitastudio.oss.model.dto.StandardInfo;
import cn.nobitastudio.oss.model.error.ErrorCode;
import cn.nobitastudio.oss.repo.*;
import cn.nobitastudio.oss.service.inter.ElectronicService;
import cn.nobitastudio.oss.service.inter.RegistrationRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/25 16:17
 * @description
 */
@Service
public class ElectronicServiceImpl implements ElectronicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElectronicServiceImpl.class);

    @Inject
    private ElectronicCaseRepo electronicCaseRepo;
    @Inject
    private RegistrationRecordRepo registrationRecordRepo;
    @Inject
    private OSSOrderRepo ossOrderRepo;
    @Inject
    private ContainRepo containRepo;
    @Inject
    private DrugRepo drugRepo;
    @Inject
    private CheckItemRepo checkItemRepo;
    @Inject
    private OperationItemRepo operationItemRepo;
    @Inject
    private MedicalCardRepo medicalCardRepo;
    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private RegistrationRecordService registrationRecordService;

    /**
     * 查询指定id的电子病历
     *
     * @param id 指定绑定关系id
     * @return
     */
    @Override
    public ElectronicCase getById(Integer id) {
        return electronicCaseRepo.findById(id).orElseThrow(() -> new AppException("未查找指定电子病历", ErrorCode.NOT_FIND_ELECTRONIC_CASE_BY_ID));
    }

    /**
     * 查询所有电子病历
     *
     * @param electronicCase
     * @param pager          分页参数
     * @return
     */
    @Override
    public PageImpl<ElectronicCase> getAll(ElectronicCase electronicCase, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(), pager.getLimit(), Sort.by(Sort.Direction.DESC, "createTime"));
        Page<ElectronicCase> electronicCases = electronicCaseRepo.findAll(SpecificationBuilder.toSpecification(electronicCase), pageable);
        return new PageImpl<>(electronicCases.getContent(), pageable, electronicCases.getTotalElements());
    }

    /**
     * 删除指定id电子病历
     *
     * @param id
     * @return
     */
    @Override
    public StandardInfo delete(Integer id) {
        electronicCaseRepo.delete(electronicCaseRepo.findById(id).orElseThrow(() -> new AppException("未查找指定电子病历", ErrorCode.NOT_FIND_ELECTRONIC_CASE_BY_ID)));
        return new StandardInfo("删除成功");
    }

    /**
     * 新增或更新电子病历信息
     *
     * @param electronicCase
     * @return
     */
    @Override
    public ElectronicCase save(ElectronicCase electronicCase) {
        return electronicCaseRepo.save(electronicCase);
    }

    /**
     * 查询指定诊疗卡的全部电子病历.根据电子病历中的就诊时间排序.
     *
     * @param medicalCardId
     * @return
     */
    @Override
    public List<ElectronicCaseDTO> findByMedicalCardId(String medicalCardId, String medicalCardPassword) {

        // 先验证诊疗卡管理密码
        if (!passwordEncoder.matches(medicalCardPassword,
                medicalCardRepo.findById(medicalCardId)
                        .orElseThrow(() -> new AppException("未查找到指定诊疗卡", ErrorCode.NOT_FIND_MEDICAL_CARD_BY_ID))
                        .getPassword())) {
            throw new AppException("诊疗卡密码错误", ErrorCode.MEDICAL_CARD_PASSWORD_ERROR);
        }

        List<ElectronicCaseDTO> electronicCaseDTOs = new ArrayList<>();
        List<RegistrationRecord> registrationRecords = registrationRecordRepo.findByMedicalCardId(medicalCardId);
        List<ElectronicCase> electronicCases = new ArrayList<>();
        for (RegistrationRecord registrationRecord : registrationRecords) {
            // 已经存在病历情况.否则即为只挂了号但是未就诊情况(未调用模拟的就诊方法)
            electronicCaseRepo.findByRegistrationRecordId(registrationRecord.getId()).ifPresent(electronicCases::add);
        }
        // 排序 electronicCases 根据diagnosis_time
        electronicCases = electronicCases.stream().sorted((o1, o2) -> o1.getDiagnosisTime().isAfter(o2.getDiagnosisTime()) ? -1 : 1).collect(Collectors.toList());
        for (ElectronicCase electronicCase : electronicCases) {
            ElectronicCaseDTO electronicCaseDTO = new ElectronicCaseDTO();
            // 查找对应的药品-检查-手术 详情
            List<Contain> contains = containRepo.findByOssOrderIdOrderByIdAsc(electronicCase.getOrderId());
            List<Drug> drugs = new ArrayList<>();
            List<Integer> drugCount = new ArrayList<>();

            List<CheckItem> checkItems = new ArrayList<>();
            List<Integer> checkItemCount = new ArrayList<>();

            List<OperationItem> operationItems = new ArrayList<>();
            List<Integer> operationItemCount = new ArrayList<>();
            for (Contain contain : contains) {
                switch (contain.getItemType()) {
                    case DRUG:
                        Drug drug = drugRepo.findById(Integer.valueOf(contain.getItemId()))
                                .orElseThrow(() -> new AppException("未查找到指定药品", ErrorCode.NOT_FIND_DRUG_BY_ID));
                        drugs.add(drug);
                        drugCount.add(contain.getAmount());
                        break;
                    case CHECK:
                        CheckItem checkItem = checkItemRepo.findById(Integer.valueOf(contain.getItemId()))
                                .orElseThrow(() -> new AppException("未查找到指定检查项", ErrorCode.NOT_FIND_CHECK_ITEM_BY_ID));
                        checkItems.add(checkItem);
                        checkItemCount.add(contain.getAmount());
                        break;
                    case OPERATION:
                        OperationItem operationItem = operationItemRepo.findById(Integer.valueOf(contain.getItemId()))
                                .orElseThrow(() -> new AppException("未查找到指定手术项", ErrorCode.NOT_FIND_OPERATION_ITEM_BY_ID));
                        operationItems.add(operationItem);
                        operationItemCount.add(contain.getAmount());
                        break;
                }
            }
            electronicCaseDTO.setOssOrder(ossOrderRepo.findById(electronicCase.getOrderId())
                    .orElseThrow(() -> new AppException("未找到指定订单", ErrorCode.NOT_FIND_ORDER_BY_ID)));  // 药品，手术，检查等对应订单实体
            electronicCaseDTO.setElectronicCase(electronicCase); //病历基础信息 - 医嘱.就诊时间等等
            electronicCaseDTO.setDrugs(drugs); // 药品信息
            electronicCaseDTO.setDrugCount(drugCount); // 药品对应的数量
            electronicCaseDTO.setCheckItems(checkItems); // 检查项
            electronicCaseDTO.setCheckItemCount(checkItemCount);//检查项对应的数量
            electronicCaseDTO.setOperationItems(operationItems);// 手术项
            electronicCaseDTO.setOperationItemCount(operationItemCount);// 手术项对应的数量
            electronicCaseDTO.setRegistrationAll(registrationRecordService.getByRegistrationRecordId(electronicCase.getRegistrationRecordId())); // 对应的挂号单全部信息
            electronicCaseDTOs.add(electronicCaseDTO);
        }
        LOGGER.info("electronicCaseDTOs.size():" + electronicCaseDTOs.size());
        return electronicCaseDTOs;
    }
}
