package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Doctor;
import cn.nobitastudio.oss.repo.DoctorRepo;
import cn.nobitastudio.oss.service.inter.DoctorService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 13:57
 * @description
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    private static final String DELETE_SUCCESS = "医生信息删除成功";
    private static final String SAVE_OR_UPDATE_SUCCESS = "医生信息添加或修改成功";

    @Inject
    private DoctorRepo doctorRepo;

    /**
     * 查询指定id医生信息
     *
     * @param id 指定医生id
     * @return
     */
    @Override
    public Doctor getById(Integer id) {
        return doctorRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定医生信息"));
    }

    /**
     * 查询所有医生,结果进行分页
     *
     * @param doctor
     * @param pager  分页参数
     * @return
     */
    @Override
    public PageImpl<Doctor> getAll(Doctor doctor, Pager pager) {
        Pageable pageable = PageRequest.of(pager.getPage(),pager.getLimit(),Sort.by(Sort.Direction.ASC,"id"));
        Page<Doctor> doctors = doctorRepo.findAll(SpecificationBuilder.toSpecification(doctor),pageable);
        return new PageImpl<>(doctors.getContent(),pageable,doctors.getTotalElements());
    }

    /**
     * 删除指定医生信息
     *
     * @param id 指定医生id
     * @return
     */
    @Override
    public String delete(Integer id) {
        doctorRepo.delete(doctorRepo.findById(id).orElseThrow(() -> new AppException("未查找到指定医生信息")));
        return DELETE_SUCCESS;
    }

    /**
     * 新增或更新医生信息信息
     *
     * @param doctor 待新增或更新的医生信息
     * @return
     */
    @Override
    public Doctor save(Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    /**
     * 查询指定用户的收藏医生,默认按照收藏时间进行倒序
     *
     * @param userId
     * @return
     */
    @Override
    public List<Doctor> getCollectDoctor(Integer userId) {
        List<Doctor> doctors = doctorRepo.findCollectDoctor(userId);
        return doctors;
    }
}
