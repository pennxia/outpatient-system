package cn.nobitastudio.oss.model.dto;

import cn.nobitastudio.oss.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/25 17:01
 * @description 通过诊疗卡查询全部的电子病历信息时的信息封装对象
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCaseDTO {

    @ApiModelProperty("产生电子病历时生成的订单实体")
    private OSSOrder ossOrder;

    @ApiModelProperty("电子病历实体信息")
    private ElectronicCase electronicCase;

    @ApiModelProperty("电子病历中的药品")
    private List<Drug> drugs;
    @ApiModelProperty("每一项药品对应的数量")
    private List<Integer> drugCount;

    @ApiModelProperty("电子病历中的检查项")
    private List<CheckItem> checkItems;
    @ApiModelProperty("各个检查项对应的数量")
    private List<Integer> checkItemCount;

    @ApiModelProperty("电子病历中的手术项")
    private List<OperationItem> operationItems;
    @ApiModelProperty("各个手术项对应的数量")
    private List<Integer> operationItemCount;

    @ApiModelProperty("挂号单全部信息")
    private RegistrationAll registrationAll;
}
