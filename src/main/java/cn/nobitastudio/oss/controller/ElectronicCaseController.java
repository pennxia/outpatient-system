package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.ElectronicCase;
import cn.nobitastudio.oss.model.dto.ElectronicCaseDTO;
import cn.nobitastudio.oss.model.dto.StandardInfo;
import cn.nobitastudio.oss.service.inter.ElectronicService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 15:27
 * @description
 */
@RestController
@RequestMapping("/electronic-case")
public class ElectronicCaseController {

    @Inject
    private ElectronicService electronicService;

    @ApiOperation("查询指定绑定关系")
    @GetMapping("/{id}")
    public ServiceResult<ElectronicCase> getById(@PathVariable("id") Integer id) {
        return ServiceResult.success(electronicService.getById(id));
    }

    @ApiOperation("查询分页后的绑定关系")
    @PutMapping("/query")
    public ServiceResult<PageImpl<ElectronicCase>> query(@RequestBody ElectronicCase electronicCase, Pager pager) {
        return ServiceResult.success(electronicService.getAll(electronicCase, pager));
    }

    @ApiOperation("删除指定绑定")
    @DeleteMapping("/{id}")
    public ServiceResult<StandardInfo> deleteById(@PathVariable("id") Integer id) {
        return ServiceResult.success(electronicService.delete(id));
    }

    @ApiOperation("保存或更新绑定信息")
    @PostMapping
    public ServiceResult<ElectronicCase> save(@RequestBody ElectronicCase electronicCase) {
        return ServiceResult.success(electronicService.save(electronicCase));
    }

    @ApiOperation("查询指定诊疗卡的全部电子病历")
    @GetMapping("/{medicalCardId}/{medicalCardPassword}/findAll")
    public ServiceResult<List<ElectronicCaseDTO>> getAllByMedicalCardId(@PathVariable(name = "medicalCardId") String medicalCardId,
                                                                        @PathVariable(name = "medicalCardPassword") String medicalCardPassword) {
        return ServiceResult.success(electronicService.findByMedicalCardId(medicalCardId, medicalCardPassword));
    }

}
