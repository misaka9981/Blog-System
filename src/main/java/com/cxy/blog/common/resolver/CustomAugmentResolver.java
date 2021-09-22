package com.cxy.blog.common.resolver;

import com.cxy.blog.common.annotation.CurrentUser;
import com.cxy.blog.common.constant.Const;
import com.cxy.blog.model.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

 
public class CustomAugmentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class) && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        if (methodParameter.getParameterType().equals(User.class)) {
            return nativeWebRequest.getAttribute(Const.USER_SESSION_KEY, RequestAttributes.SCOPE_SESSION);
        }
        return null;
    }
}
