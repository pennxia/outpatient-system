package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.AppException;
import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.criteria.CriteriaException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.MedicalCard;
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

    @ApiOperation("查询指定诊疗卡信息")
    @GetMapping("/{id}")
    public ServiceResult<MedicalCard> getById(@PathVariable("id") Integer id) {
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
    public ServiceResult<String> deleteById(@PathVariable("id") Integer id) {
        try {
            return ServiceResult.success(medicalCardService.delete(id));
        } catch (AppException e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @ApiOperation("保存或更新诊疗卡信息")
    @PostMapping
    public ServiceResult<MedicalCard> save(@RequestBody MedicalCard medicalCard) {
        return ServiceResult.success(medicalCardService.save(medicalCard));
    }
}
