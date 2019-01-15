package cn.nobitastudio.oss.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/12 16:36
 * @description
 */
public class SpringBeanUtil {

    private static ApplicationContext applicationContext;
    static final private Object lock = new Object();
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBeanUtil.class);

    /**
     * 设置上下文
     *
     * @param applicationContext
     * @throws BeansException
     */
    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        synchronized (lock) {
            if (SpringBeanUtil.applicationContext == null) {
                SpringBeanUtil.applicationContext = applicationContext;
            }
        }
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean
     *
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
