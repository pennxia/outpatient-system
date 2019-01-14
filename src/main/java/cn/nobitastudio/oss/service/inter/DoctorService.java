package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Doctor;
import org.springframework.data.domain.PageImpl;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 13:55
 * @description
 */
public interface DoctorService {

    /**
     * 查询指定id医生信息
     *
     * @param id 指定医生id
     * @return
     */
    Doctor getById(Integer id);

    /**
     * 查询所有医生,结果进行分页
     *
     * @param pager 分页参数
     * @return
     */
    PageImpl<Doctor> getAll(Doctor doctor, Pager pager);

    /**
     * 删除指定医生信息
     *
     * @param id 指定医生id
     * @return
     */
    String delete(Integer id);

    /**
     * 新增或更新医生信息信息
     *
     * @param doctor 待新增或更新的医生信息
     * @return
     */
    Doctor save(Doctor doctor);

    /**
     * 查询指定用户的收藏医生
     * @param userId
     * @return
     */
    List<Doctor> getCollectDoctor(Integer userId);
}
