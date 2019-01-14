package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.CollectDoctor;
import cn.nobitastudio.oss.repo.CollectDoctorRepo;
import cn.nobitastudio.oss.service.inter.CollectDoctorService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/04 17:47
 * @description
 */
@Service
public class CollectDoctorServiceImpl implements CollectDoctorService {

    private static final String DELETE_SUCCESS = "收藏关系删除成功";
    private static final String SAVE_OR_UPDATE_SUCCESS = "收藏关系添加或修改成功";
    private static final String UN_COLLECT_SUCCESS = "取消收藏成功";
    private static final String COLLECT_SUCCESS = "收藏成功";

    @Inject
    private CollectDoctorRepo collectDoctorRepo;

    /**
     * 查询指定id 收藏关系
     *
     * @param id 指定收藏关系id
     * @return
     */
    @Override
    public CollectDoctor getById(Integer id) {
        return collectDoctorRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定收藏关系"));
    }

    /**
     * 查询所有收藏关系,结果进行分页
     *
     * @param collectDoctor
     * @param pager         分页参数
     * @return
     */
    @Override
    public PageImpl<CollectDoctor> getAll(CollectDoctor collectDoctor, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(), pager.getLimit(), Sort.by(Sort.Direction.DESC, "createTime"));
        Page<CollectDoctor> collectDoctors = collectDoctorRepo.findAll(SpecificationBuilder.toSpecification(collectDoctor), pageable);
        return new PageImpl<>(collectDoctors.getContent(), pageable, collectDoctors.getTotalElements());
    }

    /**
     * 删除指定id收藏关系
     *
     * @param id 指定收藏关系id
     * @return
     */
    @Override
    public String delete(Integer id) {
        collectDoctorRepo.delete(collectDoctorRepo.findById(id).orElseThrow(() -> new AppException("取消收藏失败,你暂未收藏该医生")));
        return DELETE_SUCCESS;
    }

    /**
     * 新增或更新收藏信息
     *
     * @param collectDoctor 待新增或更新的收藏信息
     * @return
     */
    @Override
    public CollectDoctor save(CollectDoctor collectDoctor) {
        return collectDoctorRepo.save(collectDoctor);
    }

    /**
     * 用户收藏医生
     *
     * @param collectDoctor
     * @return
     */
    @Override
    public CollectDoctor collect(CollectDoctor collectDoctor) {
        Optional<CollectDoctor> optionalCollectDoctor = collectDoctorRepo.findByUserIdAndDoctorId(collectDoctor.getUserId(),collectDoctor.getDoctorId());
        if (optionalCollectDoctor.isPresent()) {
            throw new AppException("收藏失败,用户已收藏该医生");
        }
        return collectDoctorRepo.save(collectDoctor);
    }

    /**
     * 用户取消收藏医生
     *
     * @param collectDoctor 待取消收藏参数
     * @return
     */
    @Override
    public String unCollect(CollectDoctor collectDoctor) {
        Optional<CollectDoctor> optionalCollectDoctor = collectDoctorRepo.findByUserIdAndDoctorId(collectDoctor.getUserId(),collectDoctor.getDoctorId());
        if (!optionalCollectDoctor.isPresent()) {
            throw new AppException("取消收藏失败,用户已取消收藏该医生");
        }
        collectDoctorRepo.delete(collectDoctorRepo.findByUserIdAndDoctorId(collectDoctor.getUserId(),collectDoctor.getDoctorId()).orElseThrow(() -> new AppException("取消收藏失败,暂未收藏该医生")));
        return UN_COLLECT_SUCCESS;
    }
}
