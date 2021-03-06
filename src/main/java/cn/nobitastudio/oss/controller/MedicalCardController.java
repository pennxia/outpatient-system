package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.criteria.CriteriaException;
import cn.nobitastudio.common.exception.AppException;
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
import java.util.List;

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
        return ServiceResult.success(medicalCardService.getById(id));
    }

    @ApiOperation("查询分页后的诊疗卡信息")
    @PutMapping("/query")
    public ServiceResult<PageImpl<MedicalCard>> query(@RequestBody MedicalCard medicalCard, Pager pager) {
        return ServiceResult.success(medicalCardService.getAll(medicalCard, pager));
    }

    @ApiOperation("删除指定诊疗卡信息")
    @DeleteMapping("/{id}")
    public ServiceResult<String> deleteById(@PathVariable("id") String id) {
        return ServiceResult.success(medicalCardService.delete(id));
    }

    @ApiOperation("保存诊疗卡信息")
    @PostMapping
    public ServiceResult<MedicalCard> save(@RequestBody MedicalCard medicalCard) {
        return ServiceResult.success(medicalCardService.save(medicalCard));
    }

    @ApiOperation("更新诊疗卡信息")
    @PutMapping
    public ServiceResult<MedicalCard> modify(@RequestBody MedicalCard medicalCard) {
        return ServiceResult.success(medicalCardService.modify(medicalCard));
    }

    @ApiOperation("用户创建诊疗卡,创建完成后自动绑定诊疗卡")
    @PostMapping("/create-and-bind")
    public ServiceResult<MedicalCard> userCreateMedicalCard(@RequestBody CreateMedicalCardDTO createMedicalCardDTO) {
        MedicalCard medicalCard = medicalCardService.save(createMedicalCardDTO.getMedicalCard());
        bindService.bind(new Bind(createMedicalCardDTO.getUserId(), medicalCard.getId()));
        return ServiceResult.success(medicalCard);
    }

    @ApiOperation("查询用户绑定的诊疗卡")
    @GetMapping("/{userId}/medical-cards")
    public ServiceResult<List<MedicalCard>> findBindMedicalCards(@PathVariable(name = "userId") Integer userId) {
        return ServiceResult.success(medicalCardService.findBindMedicalCard(userId));
    }

}
