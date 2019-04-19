package cn.nobitastudio.oss.controller;

import cn.nobitastudio.oss.cache.RedisHelper;
import cn.nobitastudio.oss.model.dto.ImageValidateCode;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/14 13:55
 * @description
 */
@Controller
@RequestMapping("/image-validate")
public class ImageValidateController {

    @Inject
    private Producer producer;
    @Inject
    private RedisHelper redisHelper;
    @Value(value = "${oss.app.captcha.expireTime:3600}")
    private long captchaExpireTime;  // 验证码的保存时间 默认一个小时


    @ApiOperation("用户请求图片验证码")
    @GetMapping("/{userId}")
    public void captcha(HttpServletResponse response, @PathVariable(name = "userId") String userId) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String captcha = producer.createText();
        //生成图片验证码
        BufferedImage bufferedImage = producer.createImage(captcha);
        ImageValidateCode imageValidateCode = new ImageValidateCode(userId, captcha);
        //保存到redis
        redisHelper.set(userId, imageValidateCode, captchaExpireTime, TimeUnit.SECONDS);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpg", out);
    }

    @ApiOperation("用户请求图片验证码")
    @GetMapping("/get/{userId}")
    public void get(HttpServletResponse response, @PathVariable(name = "userId") String userId) throws IOException {
        String c = redisHelper.get(userId,ImageValidateCode.class).getCaptcha();
        response.getWriter().write(c);
//
//
////        ServletOutputStream out = response.getOutputStream();
////        ImageIO.write(bufferedImage, "jpg", out);
    }


}
