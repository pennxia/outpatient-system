package cn.nobitastudio.oss.authentication.hander;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/09 21:44
 * @description
 */
@Component
public class OSSAuthenticationSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler
        implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);  // 让前端进行了跳转
        // 做自己的操作
        logger.info("登录成功");
        clearAuthenticationAttributes(request);
    }

    /**
     * Removes temporary authentication-related data which may have been stored in the
     * session during the authentication process.
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
