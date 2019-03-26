package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.exception.AppException;
import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.model.error.ErrorCode;
import cn.nobitastudio.oss.repo.UserRepo;
import cn.nobitastudio.oss.service.inter.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

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

    @Inject
    PasswordEncoder passwordEncoder;
    @Inject
    UserRepo userRepo;

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

    @Override
    public ServiceResult<User> login(User user) {
        Optional<User> optionalUser = userRepo.findByMobile(user.getMobile());
        if (!optionalUser.isPresent()) {
            return ServiceResult.failure(user, ErrorCode.NOT_FIND_USER_BY_MOBILE);
        } else {
            if (!passwordEncoder.matches(user.getPassword(), optionalUser.get().getPassword())) {
                return ServiceResult.failure(user, ErrorCode.MOBILE_OR_PASSWORD_ERROR);
            } else {
                return ServiceResult.success(optionalUser.get());
            }
        }
    }
}
