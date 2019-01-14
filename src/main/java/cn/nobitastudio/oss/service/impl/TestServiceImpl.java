package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.oss.service.inter.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/11 09:54
 * @description
 */
@Service
public class TestServiceImpl implements TestService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Integer count = 0;

    @Transactional
    @Override
    public synchronized void test() {
        logger.info(count + "");
        count++;
    }

    @Override
    public String testConf() {
        return null;
    }


}
