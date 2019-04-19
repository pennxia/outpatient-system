package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.oss.model.dto.ConfirmValidateCodeDTO;
import cn.nobitastudio.oss.model.dto.RequestValidateCodeDTO;
import cn.nobitastudio.oss.model.dto.StandardInfo;
import cn.nobitastudio.oss.model.vo.StandardMessage;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/14 14:06
 * @description
 */
public interface ValidateService {

    /**
     * 用户请求验证码
     * @param requestValidateCodeDTO
     * @return
     */
    StandardInfo requestValidateCode(RequestValidateCodeDTO requestValidateCodeDTO);

    /**
     * 用户确认验证码
     * @param confirmValidateCodeDTO
     * @return
     */
    StandardInfo confirmValidateCode(ConfirmValidateCodeDTO confirmValidateCodeDTO);


}
