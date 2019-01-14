package cn.nobitastudio.oss;

import cn.nobitastudio.oss.util.SpringBeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class OSSApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(OSSApplication.class, args);
        SpringBeanUtil.setApplicationContext(applicationContext);
    }

}
