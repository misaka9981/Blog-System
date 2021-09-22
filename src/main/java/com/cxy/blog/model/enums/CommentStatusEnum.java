package com.cxy.blog.model.enums;

 
public enum CommentStatusEnum {


     
    CHECKING(0, "待审核"),

     
    PUBLISHED(1, "已发布"),

     
    RECYCLE(2, "已删除");

    private int value;
    private String desc;

    CommentStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
