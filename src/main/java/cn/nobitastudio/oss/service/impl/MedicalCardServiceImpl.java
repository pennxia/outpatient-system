package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.oss.repo.MedicalCardRepo;
import cn.nobitastudio.oss.service.inter.MedicalCardService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:36
 * @description
 */
@Service
public class MedicalCardServiceImpl implements MedicalCardService {

    @Inject
    private MedicalCardRepo medicalCardRepo;


}
