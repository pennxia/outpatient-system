package cn.nobitastudio.oss.shiro;

import cn.nobitastudio.common.ServiceResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.MimeType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/10 17:34
 * @description
 */
public class RestFormAuthenticationFilter extends FormAuthenticationFilter{

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            String contentType = request.getContentType();
            if (contentType != null && MimeType.valueOf("text/*").includes(MimeType.valueOf(contentType))) {
                saveRequestAndRedirectToLogin(request, response);
            } else {
                // save request
                saveRequest(request);
                // return json
                WebUtils.toHttp(response).setStatus(401);
                ServiceResult<String> result = new ServiceResult<>();
                result.setState(ServiceResult.STATE_NO_SESSION);
                result.setError("Permission denied");
                ObjectMapper mapper = new ObjectMapper();
                byte[] payload = mapper.writeValueAsBytes(result);
                response.getOutputStream().write(payload);
                response.flushBuffer();
            }
            return false;
        }
    }
}
