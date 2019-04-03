package cn.nobitastudio.oss.model.dto;

import cn.nobitastudio.oss.model.enumeration.PaymentChannel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/03 10:39
 * @description  图灵支付回调参数封装
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrPayCallbackParam {

    // 公共入参
    @ApiModelProperty("软件的appkey")
    private String appkey;

    @ApiModelProperty("请求方法")
    private String method;

    @ApiModelProperty("签名,MD5加密")
    private String sign;

    @ApiModelProperty("时间戳")
    private String timestamp;

    @ApiModelProperty("订单号")
    private String version;

    // 异步通知业务参数
    @ApiModelProperty("订单号")
    private String outTradeNo;

    @ApiModelProperty("订单类型：1支付宝2微信3银联")
    private String payType;

    @ApiModelProperty("商品名称")
    private String tradeName;

    @ApiModelProperty("订单金额，单位分")
    private String amount;

    @ApiModelProperty("支付状态：1.未支付2.支付成功.3支付失败")
    private String status;

    @ApiModelProperty("通知时间")
    private String notifyTime;

    @ApiModelProperty("支付用户id")
    private String payUserId;

    @ApiModelProperty("渠道")
    private String channel;

    @ApiModelProperty("回传参数，以逗号分隔")
    private String backParams;

    public TrPayCallbackParam(TreeMap<String, String> paramMap) {
        this.appkey = paramMap.get("appkey");
        this.method = paramMap.get("method");
        this.sign = paramMap.get("sign");
        this.timestamp = paramMap.get("timestamp");
        this.version = paramMap.get("version");
        this.outTradeNo = paramMap.get("outTradeNo");
        this.payType = paramMap.get("payType");
        this.tradeName = paramMap.get("tradeName");
        this.amount = paramMap.get("amount");
        this.status = paramMap.get("status");
        this.notifyTime = paramMap.get("notifyTime");
        this.payUserId = paramMap.get("payUserId");
        this.channel = paramMap.get("channel");
        this.backParams = paramMap.get("backParams");
    }

    public PaymentChannel getTrPayCallbackParamByPayType() {
        if (this.payType.equals("1")) {
            return PaymentChannel.ALI_PAY;
        } else if (this.payType.equals("2")) {
            return PaymentChannel.WECHAT_PAY;
        } else if (this.payType.equals("3")) {
            return PaymentChannel.UNION_PAY;
        } else {
            return PaymentChannel.UNDEFINED;
        }
    }

}
