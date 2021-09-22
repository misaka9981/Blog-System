package com.cxy.blog.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

 
@Slf4j
@ControllerAdvice(basePackages = "com.cxy.blog.controller.front")
public class FrontExceptionHandler {

     
    @ExceptionHandler(BlogException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerRRException(BlogException e){
        log.error(e.getMessage(), e);
        return "error/404";
    }

     
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerException(Exception e){
        log.error(e.getMessage(), e);
        return "error/500";
    }
}
