package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.criteria.CriteriaException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Bind;
import cn.nobitastudio.oss.entity.MedicalCard;
import cn.nobitastudio.oss.model.dto.CreateMedicalCardDTO;
import cn.nobitastudio.oss.service.inter.BindService;
import cn.nobitastudio.oss.service.inter.MedicalCardService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:31
 * @description
 */
@RestController
@RequestMapping("/medical-card")
public class MedicalCardController {

    @Inject
    private MedicalCardService medicalCardService;
    @Inject
    private BindService bindService;

    @ApiOperation("查询指定诊疗卡信息")
    @GetMapping("/{id}")
    public ServiceResult<MedicalCard> getById(@PathVariable("id") String id) {
        try {
            return ServiceResult.success(medicalCardService.getById(id));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("查询分页后的诊疗卡信息")
    @PutMapping("/query")
    public ServiceResult<PageImpl<MedicalCard>> query(@RequestBody MedicalCard medicalCard, Pager pager) {
        try {
            return ServiceResult.success(medicalCardService.getAll(medicalCard, pager));
        } catch (CriteriaException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("删除指定诊疗卡信息")
    @DeleteMapping("/{id}")
    public ServiceResult<String> deleteById(@PathVariable("id") String id) {
        try {
            return ServiceResult.success(medicalCardService.delete(id));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("保存诊疗卡信息")
    @PostMapping
    public ServiceResult<MedicalCard> save(@RequestBody MedicalCard medicalCard) {
        return ServiceResult.success(medicalCardService.save(medicalCard));
    }

    @ApiOperation("更新诊疗卡信息")
    @PutMapping
    public ServiceResult<MedicalCard> modify(@RequestBody MedicalCard medicalCard) {
        try {
            return ServiceResult.success(medicalCardService.modify(medicalCard));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("用户创建诊疗卡,创建完成后自动绑定诊疗卡")
    @PostMapping("/create-and-bind")
    public ServiceResult<MedicalCard> userCreateMedicalCard(@RequestBody CreateMedicalCardDTO createMedicalCardDTO) {
        try {
            MedicalCard medicalCard = medicalCardService.save(createMedicalCardDTO.getMedicalCard());
            bindService.bind(new Bind(createMedicalCardDTO.getUserId(),medicalCard.getId()));
            return ServiceResult.success(medicalCard);
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

}
