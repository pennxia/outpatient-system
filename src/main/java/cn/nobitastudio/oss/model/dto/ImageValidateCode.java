package cn.nobitastudio.oss.model.dto;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/01 19:39
 * @description 验证码实体
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageValidateCode implements Serializable {
    @ApiModelProperty("请求的用户id")
    private String userId;

    @ApiModelProperty("过期时间")
    private LocalDateTime expireTime;

    @ApiModelProperty("验证码")
    private String captcha;

    public ImageValidateCode(String userId, String captcha) {
        this.userId = userId;
        this.captcha = captcha;
        this.expireTime = LocalDateTime.now().plusSeconds(120);   // 有效期仅为两分钟
    }
}
