package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.RegistrationRecord;
import cn.nobitastudio.oss.model.dto.ConfirmRegisterDTO;
import cn.nobitastudio.oss.model.dto.RegisterDTO;
import cn.nobitastudio.oss.model.dto.ConfirmOrCancelRegisterDTO;
import cn.nobitastudio.oss.model.dto.RegistrationAll;
import cn.nobitastudio.oss.model.vo.RegistrationBasicInfoCollection;
import cn.nobitastudio.oss.model.vo.RegistrationRecordAndOrder;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/06 15:13
 * @description
 */
public interface RegistrationRecordService {


    /**
     * 查询指定id挂号记录信息
     *
     * @param id 挂号记录id
     * @return
     */
    RegistrationRecord getById(String id);

    /**
     * 查询所有挂号记录,结果进行分页
     *
     * @param pager 分页参数
     * @return
     */
    PageImpl<RegistrationRecord> getAll(RegistrationRecord registrationRecord, Pager pager);

    /**
     * 删除指定挂号记录信息
     *
     * @param id 指定挂号记录id
     * @return
     */
    String delete(String id);

    /**
     * 新增或更新挂号记录
     *
     * @param registrationRecord 待新增或更新的挂号记录
     * @return
     */
    RegistrationRecord save(RegistrationRecord registrationRecord);

    /**
     * 用户进行挂号操作
     *
     * @param registerDTO
     * @return
     */
    @Transactional
    RegistrationAll register(RegisterDTO registerDTO);

    /**
     * 用户支付完成.确认还挂号单以及对应的订单.
     *
     * @param confirmRegisterDTO
     * @return
     */
    ConfirmOrCancelRegisterDTO confirmRegister(ConfirmRegisterDTO confirmRegisterDTO);

    /**
     * 用户取消预约该挂号单.
     * @param id
     * @return
     */
    ConfirmOrCancelRegisterDTO cancelRegister(String id);

    /**
     * 得到挂号单以及对应的订单
     * @param userId
     * @return
     */
    List<RegistrationRecordAndOrder> getRegistrationAndOrder(Integer userId);

    /**
     * 查询指定挂号单基础信息集合详情
     * @param registrationRecordId
     * @return
     */
    RegistrationBasicInfoCollection getRegistrationBasicInfoCollection(String registrationRecordId);

    /**
     * 获取指定用户的挂号单信息抽象集合详情
     * @param userId
     * @return
     */
    List<RegistrationAll> getRegistrationAll(Integer userId);

    /**
     * 通过 registrationRecordId 查询挂号单信息抽象集合详情
     * @param registrationRecordId
     * @return
     */
    RegistrationAll getByRegistrationRecordId(String registrationRecordId);
}
