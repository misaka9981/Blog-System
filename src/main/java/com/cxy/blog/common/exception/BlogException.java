package com.cxy.blog.common.exception;

import com.cxy.blog.common.constant.CodeEnum;

 
public class BlogException extends RuntimeException {

    private String msg;
    private int code = CodeEnum.UNKNOWN_ERROR.getValue();

    public BlogException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BlogException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BlogException(int code,String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BlogException(int code, String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
