package cn.nobitastudio.oss.model.vo;

import cn.nobitastudio.oss.entity.HealthArticle;
import cn.nobitastudio.oss.entity.MedicalCard;
import cn.nobitastudio.oss.entity.User;
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
 * @date 2019/01/16 12:23
 * @description 用户登录成功后,返回结果的集合
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResult implements Serializable {

    private static final long serialVersionUID = 7668766782546903694L;

    @ApiModelProperty("登录成功的用户基本信息,擦去了密码信息")
    private User user;

    @ApiModelProperty("用户绑定的诊疗卡")
    private List<MedicalCard> medicalCards;

    @ApiModelProperty("用户收藏的医生及医生对应的科室封装信息")
    private List<DoctorAndDepartment> doctorAndDepartments;

    @ApiModelProperty("健康资讯")
    private List<HealthArticle> healthArticles;
}
