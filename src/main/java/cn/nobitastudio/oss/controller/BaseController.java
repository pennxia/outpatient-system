//package cn.nobitastudio.oss.controller;
//
///**
// * @author chenxiong
// * @email nobita0522@qq.com
// * @date 2019/03/11 17:18
// * @description 基础Controller
// */
//public class BaseController<T> {
//
//
//
//    @ApiOperation("查询指定绑定关系")
//    @GetMapping("/{id}")
//    public ServiceResult<Bind> getById(@PathVariable("id") Integer id) {
//        try {
//            return ServiceResult.success(bindService.getById(id));
//        } catch (AppException e) {
//            return ServiceResult.failure(e.getMessage());
//        }
//    }
//
//    @ApiOperation("查询分页后的绑定关系")
//    @PutMapping("/query")
//    public ServiceResult<PageImpl<Bind>> query(@RequestBody Bind bind, Pager pager) {
//        try {
//            return ServiceResult.success(bindService.getAll(bind, pager));
//        } catch (CriteriaException e) {
//            return ServiceResult.failure(e.getMessage());
//        }
//    }
//
//    @ApiOperation("删除指定绑定")
//    @DeleteMapping("/{id}")
//    public ServiceResult<String> deleteById(@PathVariable("id") Integer id) {
//        try {
//            return ServiceResult.success(bindService.delete(id));
//        } catch (AppException e) {
//            return ServiceResult.failure(e.getMessage());
//        }
//    }
//
//    @ApiOperation("保存或更新绑定信息")
//    @PostMapping
//    public ServiceResult<Bind> save(@RequestBody Bind bind) {
//        return ServiceResult.success(bindService.save(bind));
//    }
//}
