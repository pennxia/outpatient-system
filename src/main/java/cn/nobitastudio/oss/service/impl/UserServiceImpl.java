package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.repo.UserRepo;
import cn.nobitastudio.oss.service.inter.UserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepo userRepo;

    @Override
    public User getById(Integer id) throws Exception {
        return userRepo.findById(id).orElseThrow(() -> new Exception("未查找到指定id的用户信息"));
    }

    @Override
    public User getByPhone(String phone) throws Exception {
        return userRepo.findByPhone(phone).orElseThrow(() -> new Exception("未查找phone对应的用户信息"));
    }
}
