package cn.nobitastudio.oss.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 15:27
 * @description 添加基于Redis的缓存
 */
@Component
public class RedisLoader<T> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    public T load(String cacheKey, Callable<T> loader) {
        return doLoad(cacheKey, loader, (ValueOperations valueOperations, T v) -> {
            valueOperations.set(cacheKey, v);
        });
    }

    // 带时效性
    public T load(String cacheKey, Callable<T> loader, long time, TimeUnit unit) {
        return doLoad(cacheKey, loader, (ValueOperations valueOperations, T v) -> {
            valueOperations.set(cacheKey, v, time, unit);
        });
    }


    private T doLoad(String cacheKey, Callable<T> loader, BiConsumer<ValueOperations, T> setter) {
        boolean exists = redisTemplate.hasKey(cacheKey);
        ValueOperations<String, T> opsForValue = redisTemplate.opsForValue();
        if (exists) {
            return opsForValue.get(cacheKey);
        }
        T value;
        try {
            value = loader.call();
        } catch (Exception e) {
            logger.error("查询Key：{} 时发生错误！", e);
            throw new RuntimeException();
        }
        setter.accept(opsForValue, value);
        return value;
    }
}
