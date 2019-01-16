package cn.nobitastudio.oss.model.normal;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/07 10:38
 * @description
 */
@Getter
@Setter
public class InitSchedulerJobVO {
    private JobDetailVO jobDetailVO;
    private TriggerVO triggerVO;
}