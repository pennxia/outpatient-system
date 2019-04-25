package cn.nobitastudio.oss.controller;

import cn.nobitastudio.oss.model.dto.ConfirmRegisterDTO;
import cn.nobitastudio.oss.model.dto.TrPayCallbackParam;
import cn.nobitastudio.oss.repo.TestRepo;
import cn.nobitastudio.oss.service.inter.RegistrationRecordService;
import com.alibaba.fastjson.JSON;
import com.iwellmass.core.util.MD5;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/03 10:35
 * @description
 */
@Controller
@RequestMapping("/pay-callback")
public class TrPayController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    TestRepo testRepo;
    @Inject
    RegistrationRecordService registrationRecordService;

    @Value(value = "${oss.app.trPay.appKey}")
    String trAppKey;
    @Value(value = "${oss.app.trPay.appSecret}")
    String appSecret;

    @ApiOperation("图灵支付回调:挂号单,backParams:registrationId")
    @PostMapping("/register")
    public void trPayCallback(HttpServletRequest req, HttpServletResponse response) throws IOException {
        TrPayCallbackParam trPayCallbackParam = validateSign(req);
        if (trPayCallbackParam != null) {
            // sign正确 支付状态：1.未支付2.支付成功.3支付失败
            if (trPayCallbackParam.getStatus().equals("1")) {
                // 未支付 等待支付
                // do nothing
                logger.info("未支付");
            } else if (trPayCallbackParam.getStatus().equals("2")) {
                // 支付成功
                registrationRecordService.confirmRegister(new ConfirmRegisterDTO(trPayCallbackParam.getBackParams(),
                        trPayCallbackParam.getTrPayCallbackParamByPayType()));
            } else if (trPayCallbackParam.getStatus().equals("3")) {
                // 支付失败 等待重新支付
                // do nothing
                logger.info("支付失败");
            }
            response.getWriter().write("success"); // 不再通知
        }
    }

    // 通过MD5校验
    private TrPayCallbackParam validateSign(HttpServletRequest req) {
        TreeMap<String, String> paramMap = new TreeMap<>();
        initCallbackResult(req, paramMap);

        Set<Map.Entry<String, String>> entrySet = paramMap.entrySet();
        StringBuilder normalParam = new StringBuilder();
        for (Map.Entry<String, String> entry : entrySet) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!key.equals("sign")) {
                // sign 不参与签名
                normalParam.append(key + "=" + value + "&");
            }
        }
        normalParam.append("appSceret=" + appSecret);
        logger.info(normalParam.toString());
        String sign = MD5.calc(normalParam.toString()).toUpperCase(); // 默认大写
        if (sign.equals(paramMap.get("sign"))) {
            // 签名正确才处理
            logger.info("sign1 :" + sign);
            logger.info("sign2 :" + paramMap.get("sign"));
            return new TrPayCallbackParam(paramMap);
        } else {
            logger.info("正确的sign :" + sign);
            logger.info("错误的sign :" + paramMap.get("sign"));
            return null;
        }
    }

    private void initCallbackResult(HttpServletRequest req, TreeMap<String, String> paramMap) {
        for (Iterator iter = req.getParameterMap().keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) req.getParameterMap().get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            paramMap.put(name, valueStr);
        }
        logger.info(JSON.toJSONString(paramMap));
    }



}
