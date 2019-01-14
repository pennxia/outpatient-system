package cn.nobitastudio.oss.model.dto;

import cn.nobitastudio.oss.entity.MedicalCard;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/13 23:57
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMedicalCardDTO implements Serializable {
    
    private static final long serialVersionUID = 5989074046266534712L;

    @ApiModelProperty("创建诊疗卡用户的id")
    private Integer userId;
    
    @ApiModelProperty("创建的诊疗卡")
    private MedicalCard medicalCard;
}
