package cn.nobitastudio.oss.model.dto;

import cn.nobitastudio.oss.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/31 13:17
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult implements Serializable {

    @ApiModelProperty("用户信息")
    private User user;

    @ApiModelProperty("绑定的诊疗卡信息")
    private List<MedicalCard> medicalCards;

    @ApiModelProperty("设置属性：是否推送等待")
    private List<SettingAttr> settingAttrs;

    @ApiModelProperty("用户消息")
    private List<Info> infos;

    @ApiModelProperty("收藏的医生")
    private List<Doctor> doctors;
}
