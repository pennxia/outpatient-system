package cn.nobitastudio.oss.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: zhangt
 * @Date: 2018-12-7 14:51
 * @Description: redis工具类
 */
@Component
public final class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 存入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据Key获取数据
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public Map<String, Object> get(List<String> keys) {
        Map<String, Object> map = new HashMap<>();
        keys.forEach(key -> {
            Object value = redisTemplate.opsForValue().get(key);
            map.put(key, value);
        });
        return map;
    }

    /**
     * 获取redis所有数据
     *
     * @return
     */
    public Map<String, Object> getAll() {
        Set<String> keys = redisTemplate.keys("*");
        Map<String, Object> map = new HashMap<>();
        keys.forEach(key -> {
            Object value = redisTemplate.opsForValue().get(key);
            map.put(key, value);
        });
        return map;
    }

    /**
     * 删除指定的数据
     *
     * @param key
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 删除指定的数据
     *
     * @param keys
     */
    public void del(List<String> keys) {
        redisTemplate.delete(keys);
    }

    public boolean append(String key, Object value) {
        try {
            Object oldValue = get(key);
            Object newValue = oldValue == null ? value : oldValue + "," + value;
            set(key, newValue);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean rpush(String key, Object value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Object lpop(String key) {
        Object o = redisTemplate.opsForList().leftPop(key);
        return o == null ? "" : o;
    }

    public boolean expire(String key, Integer time, TimeUnit timeUnit) {
        try {
            if (redisTemplate.getExpire(key) == -1) {
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
