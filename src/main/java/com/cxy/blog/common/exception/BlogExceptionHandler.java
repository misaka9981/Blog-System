package com.cxy.blog.common.exception;

import com.cxy.blog.common.util.Result;
import com.cxy.blog.common.constant.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

 
@Slf4j
@RestControllerAdvice(basePackages = "com.cxy.blog.controller.api")
public class BlogExceptionHandler {

     
    @ExceptionHandler(BlogException.class)
    public Result handlerRRException(BlogException e) {
        log.error(e.getMessage(), e);
        return new Result(e.getCode(), e.getMsg());
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return new Result(CodeEnum.DUPLICATE_KEY.getValue(), "数据库中已存在该记录");
    }

     
    @ExceptionHandler(ValidationException.class)
    public Result handle(ValidationException e) {
        StringBuilder msg = new StringBuilder();
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                                 msg.append(item.getMessage());
            }
        } else {
            msg.append(e.getMessage());
        }
        log.error(msg.toString(), e);
        return new Result(CodeEnum.VALIDATION_ERROR.getValue(), msg.toString());
    }

     
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e) {
        log.error(e.getMessage(), e);
        return new Result(CodeEnum.UNKNOWN_ERROR.getValue(), "未知错误！");
    }
}
