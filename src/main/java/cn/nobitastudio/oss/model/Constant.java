package cn.nobitastudio.oss.model;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/03 14:27
 * @description 常量
 */
public class Constant {
    // 客户端
    public static final String LOCAL_HOST_IP = "10.0.2.2";  // Android 虚拟机内置访问本地服务地址 -> localhost
    public static final String OSS_SERVER_ONLINE = "http://www.nobitastudio.cn";  // 线上地址
    public static final String OSS_SERVER_LOCAL = "http://10.0.2.2";  // 本地服务

    public static final String OSS_SERVER_RUNTIME = OSS_SERVER_LOCAL; // 运行地址
    // 图灵支付
    public static final String OSS_PAY_CALLBACK_URL = OSS_SERVER_ONLINE + "/pay-callback/register" ; // 挂号单支付成功后的回调地址
    public final static String TR_PAY_ORDER_QUERY_URL = "http://pay.trsoft.xin/order/trpayGetWay"; // 订单查询
    public final static String TR_PAY_CHANEL = "OSS_APP_ANDROID"; // 订单查询
    public final static String OSS_TR_PAY_APP_KEY = "dbda983d39d84ba380342b692959d789"; // 图灵支付appkey
    public final static String OSS_TR_PAY_APP_SECRET = "c6ecab0e551744489c9c771e468ce3e1"; // 图灵支付 app secret
    public final static String TEST_TR_PAY_APP_KEY = "be6c44e655104d3d90e0d42432eb3c4d"; // 图灵支付appkey
    public final static String TEST_TR_PAY_APP_SECRET = "ba16f60bbb634a7aa406e883ae92e4a4"; // 图灵支付 app secret
    public final static String TR_PAY_APP_KEY = TEST_TR_PAY_APP_KEY;
    public final static String TR_PAY_APP_SECRET = TEST_TR_PAY_APP_SECRET;
}
