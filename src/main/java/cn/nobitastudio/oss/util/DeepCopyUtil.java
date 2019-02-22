package cn.nobitastudio.oss.util;

import com.alibaba.fastjson.JSON;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/02/22 16:39
 * @description 深度拷贝
 */
public class DeepCopyUtil {

    // 利用序列化实现深度 copy
    public static <T> T clone(Object source, Class<T> clazz) {
        return JSON.parseObject(JSON.toJSONString(source), clazz);
    }
}
