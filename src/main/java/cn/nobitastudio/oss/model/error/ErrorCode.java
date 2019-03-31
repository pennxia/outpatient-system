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
    public static Map<String, String> errorCodeContainer;
    // key
    public static final String UNDEFINED_ERROR = "UNDEFINED_ERROR";  // 未定义错误
    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";  // 400 500 404等错误 未知错误
    public static final String NOT_FIND_USER_BY_MOBILE = "NOT_FIND_USER_BY_MOBILE";
    public static final String MOBILE_OR_PASSWORD_ERROR = "MOBILE_OR_PASSWORD_ERROR";
    public static final String HAS_COLLECT_DOCTOR = "HAS_COLLECT_DOCTOR";

    public static String get(String errorCode) {
        if (errorCodeContainer == null) {
            initErrorCode();
        }
        return errorCodeContainer.getOrDefault(errorCode, errorCodeContainer.get(UNDEFINED_ERROR));
    }

    private static synchronized void initErrorCode() {
        errorCodeContainer = new HashMap<>();
        errorCodeContainer.put(UNDEFINED_ERROR, "未定义错误");
        errorCodeContainer.put(UNKNOWN_ERROR, "发生未知错误,请联系系统管理员");
        errorCodeContainer.put(NOT_FIND_USER_BY_MOBILE, "该手机号尚未注册");
        errorCodeContainer.put(MOBILE_OR_PASSWORD_ERROR, "账号或密码错误");
        errorCodeContainer.put(HAS_COLLECT_DOCTOR,"您以收藏该医生,请勿重复收藏");
    }

}
