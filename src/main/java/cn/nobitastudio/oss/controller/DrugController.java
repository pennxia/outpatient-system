package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.criteria.CriteriaException;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Drug;
import cn.nobitastudio.oss.service.inter.DrugService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 15:59
 * @description
 */
@RestController
@RequestMapping("/drug")
public class DrugController {

    @Inject
    private DrugService drugService;

    @ApiOperation("查询指定药品信息")
    @GetMapping("/{id}")
    public ServiceResult<Drug> getById(@PathVariable("id") Integer id) {
        return ServiceResult.success(drugService.getById(id));
    }

    @ApiOperation("查询分页后的药品信息")
    @PutMapping("/query")
    public ServiceResult<PageImpl<Drug>> query(@RequestBody Drug drug, Pager pager) {
        return ServiceResult.success(drugService.getAll(drug, pager));
    }

    @ApiOperation("删除指定药品信息")
    @DeleteMapping("/{id}")
    public ServiceResult<String> deleteById(@PathVariable("id") Integer id) {
        return ServiceResult.success(drugService.delete(id));
    }

    @ApiOperation("保存或更新药品信息")
    @PostMapping
    public ServiceResult<Drug> save(@RequestBody Drug drug) {
        return ServiceResult.success(drugService.save(drug));
    }
}
