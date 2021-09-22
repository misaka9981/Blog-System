package com.cxy.blog.common.annotation;

import com.cxy.blog.common.constant.Const;

import java.lang.annotation.*;

 
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
     
    String value() default Const.USER_SESSION_KEY;

}