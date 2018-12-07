package cn.nobitastudio.oss.service.impl;

import cn.nobitastudio.common.criteria.SpecificationBuilder;
import cn.nobitastudio.oss.entity.User;
import cn.nobitastudio.oss.repo.UserRepo;
import cn.nobitastudio.oss.service.inter.UserService;
import cn.nobitastudio.oss.vo.UserQueryVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

    @Override
    public PageImpl<User> query(UserQueryVO userQueryVO, PageRequest pageRequest) {
        Specification<User> spec = userQueryVO == null ? null : SpecificationBuilder.toSpecification(userQueryVO);
        Page<User> users = userRepo.findAll(spec,pageRequest);
        return new PageImpl<>(users.getContent(),pageRequest,users.getTotalElements());
    }
}
