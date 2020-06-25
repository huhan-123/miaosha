package cn.edu.wust.miaosha.common.config;

import cn.edu.wust.miaosha.entity.MiaoshaUser;
import cn.edu.wust.miaosha.service.MiaoshaUserService;
import cn.edu.wust.miaosha.service.impl.MiaoshaUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: huhan
 * @Date 2020/6/23 9:44 下午
 * @Description 参数解析器（如果方法参数中有MiaoshaUser，则尝试解析request填充参数值）
 * @Verion 1.0
 */
@Component
public class MiaoshaUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //如果方法参数是MiaoshaUser类型，则调用resolveArgument方法解析参数
        if (methodParameter.getParameterType() == MiaoshaUser.class) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieToken(request, MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)) {
            return null;
        }
        String token = StringUtils.isNotEmpty(paramToken)?paramToken:cookieToken;
        return miaoshaUserService.getByToken(response, token);
    }

    private String getCookieToken(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return "";
    }
}
