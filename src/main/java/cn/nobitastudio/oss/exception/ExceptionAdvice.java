package cn.nobitastudio.oss.exception;

import cn.nobitastudio.common.exception.AppException;
import com.iwellmass.common.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvice {
    private Logger logger = LoggerFactory.getLogger(getClass());


//    @ExceptionHandler({ResponseException.class})
//    public ServiceResult<String> handBizException(HttpServletRequest request, ResponseException ex) throws Exception {
//        logError(ex,request);
//        return  ServiceResult.failure("查询超时:"+ex.getMessage());
//    }

    @ExceptionHandler({AppException.class})
    @ResponseBody
    public ServiceResult<String> handAppException(HttpServletRequest request, AppException e) throws Exception {
        logError(e, request);
        return ServiceResult.failure(e);
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ServiceResult<String> handException(HttpServletRequest request, Exception e) throws Exception {
        logError(e, request);
        return ServiceResult.failure("未知错误:" + e.getMessage());
    }


    private void logError(Exception ex) {
        Map<String, String> map = new HashMap<>();
        map.put("message", ex.getMessage());
        logger.error(ex.getMessage(), ex);
    }

    private void logError(Exception ex, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("from", request.getRemoteAddr());
        String queryString = request.getQueryString();
        map.put("path", queryString != null ? (request.getRequestURI() + "?" + queryString) : request.getRequestURI());

        logger.error(ex.getMessage(), ex);
    }
}
