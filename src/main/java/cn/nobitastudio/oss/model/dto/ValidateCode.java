package cn.nobitastudio.oss.model.dto;

import cn.nobitastudio.oss.util.CommonUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/14 13:33
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = 3394256602040426665L;

    private static final Integer DEFAUL_EFFCTIVE_TIME = 120; // 默认时间有效期为120秒

    /**
     * 创建具有有限期的验证码,默认传入以秒为单位有效时间长度
     *
     * @param effictiveSeconds
     */
    public ValidateCode(Integer effictiveSeconds) {
        this.code = String.valueOf(CommonUtil.getRandom(100000, 999999));
        this.expireTime = LocalDateTime.now().plusSeconds(effictiveSeconds);
    }

    /**
     * 创建具有默认时间有限期的验证码
     */
    public ValidateCode() {
        this.code = String.valueOf(CommonUtil.getRandom(100000, 999999));
        this.expireTime = LocalDateTime.now().plusSeconds(DEFAUL_EFFCTIVE_TIME);
    }

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("到期时间")
    private LocalDateTime expireTime;
}
