package cn.nobitastudio.oss.model.enumeration;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 11:46
 * @description 订单状态
 */
public enum OrderState {

    @ApiModelProperty("已经支付")
    HAVE_PAY,
    @ApiModelProperty("等待支付")
    WAITING_PAY,
    @ApiModelProperty("取消支付")
    CANCEL_PAY,
    @ApiModelProperty("自动取消")
    AUTO_CANCEL_PAY;

    public List<String> getChineseMean() {
        List<String> chineseMean = new ArrayList<>();
        for (OrderState s : OrderState.values()) {
            if (s.equals(OrderState.HAVE_PAY)) {
                chineseMean.add("已支付");
            } else if (s.equals(OrderState.WAITING_PAY)) {
                chineseMean.add("待支付");
            } else if (s.equals(OrderState.CANCEL_PAY)) {
                chineseMean.add("已取消");
            } else if (s.equals(OrderState.AUTO_CANCEL_PAY)) {
                chineseMean.add("自动取消");
            }
        }
        return chineseMean;
    }

    // 根据位置获取对应枚举值
    public OrderState getFromPosition(int pos) {
        for (OrderState s : OrderState.values()) {
            if (pos == s.ordinal()) {
                return s;
            }
        }
        return null;
    }

}
