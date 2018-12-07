package cn.nobitastudio.oss.util;

import cn.nobitastudio.common.AppException;

import java.lang.reflect.Field;

/**
 * created by chenxiong 20181207
 */
public class Utility {

    /**
     * 检查对象中的属性是否包含null
     * @param object
     * @return
     * @throws AppException
     */
    public static void checkObjectFieldIsNull(Object object) throws AppException, IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(object) == null) {
                // 包含null
                throw new AppException("包含未初始化参数");
            }
        }
    }


}
