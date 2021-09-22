package com.cxy.blog.common.interceptor;

import com.cxy.blog.common.constant.Const;
import com.cxy.blog.model.User;
import com.cxy.blog.common.constant.CodeEnum;
import com.cxy.blog.common.exception.BlogException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

 
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                 if (!(handler instanceof HandlerMethod)) {
            return true;
        }
                 User user = (User) request.getSession().getAttribute(Const.USER_SESSION_KEY);
        if (Objects.nonNull(user)) {
            return true;
        }
                 throw new BlogException(CodeEnum.NOT_LOGIN.getValue(), "未登录！");
    }
}
