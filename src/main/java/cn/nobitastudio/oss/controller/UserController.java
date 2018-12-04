package cn.nobitastudio.oss.controller;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.oss.entity.CcTemp;
import cn.nobitastudio.oss.repo.CcTempRepo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class UserController {

    @Inject
    private CcTempRepo ccTempRepo;

    @GetMapping("/test")
    public String test() {
        return "a";
    }

    @PostMapping("/111")
    public ServiceResult<CcTemp> show2(@RequestBody CcTemp ccTemp){
        return ServiceResult.success(ccTempRepo.save(ccTemp));
    }

}
