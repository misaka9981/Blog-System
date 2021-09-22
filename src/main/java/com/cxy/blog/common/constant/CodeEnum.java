package com.cxy.blog.common.constant;

 
public enum CodeEnum {
     
    SUCCESS(1),
     
    FAIL(0),
     
    UNKNOWN_ERROR(-1),
     
    FORBIDDEN(403),
     
    NOT_LOGIN(40001),

     
    VALIDATION_ERROR(40002),

     
    DUPLICATE_KEY(40003),

     
    NOT_FOUND(40004),

     
    SPIDER_ERROR(40005),

     
    UPLOAD_ERROR(40006);

    private int value;

    CodeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
