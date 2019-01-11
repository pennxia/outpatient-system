package cn.nobitastudio.oss.service.inter;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/11 09:54
 * @description
 */
public interface TestService {

    @Transactional
    public void test() ;
}
