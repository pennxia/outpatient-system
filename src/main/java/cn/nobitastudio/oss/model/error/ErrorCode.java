package cn.nobitastudio.oss.model.error;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/19 14:17
 * @description 服务端返回的错误码
 */
public class ErrorCode {

    // 错误码
    public static Map<String,String> errorCode;
    // key
    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    public static final String NOT_FIND_USER_BY_MOBILE = "NOT_FIND_USER_BY_MOBILE";
    public static final String MOBILE_OR_PASSWORD_ERROR = "MOBILE_OR_PASSWORD_ERROR";

    public static String get(String key) {
        if (errorCode == null) {
            initErrorCode();
        }
        return errorCode.get(key);
    }

    private static synchronized void initErrorCode() {
        errorCode = new HashMap<>();
        errorCode.put("UNKNOWN_ERROR","发生未知错误,请联系系统管理员");
        errorCode.put("NOT_FIND_USER_BY_MOBILE","该手机号尚未注册");
        errorCode.put("MOBILE_OR_PASSWORD_ERROR","账号或密码错误");
    }

}
