package oss.controller;

import cn.nobitastudio.oss.entity.CcTemp;
import cn.nobitastudio.oss.repo.CcTempRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.nobitastudio.oss.OSSApplication;
import org.springframework.web.bind.annotation.RestController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OSSApplication.class)
@RestController
public class JunitTest {

    @Autowired
    private CcTempRepo ccTempRepo;

    @Test
    public void show(){
        try {
            CcTemp ccTemp = ccTempRepo.findById(1).orElseThrow(() -> new Exception("11"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(111);
        }
    }
}
