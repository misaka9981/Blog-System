package com.cxy.blog.model.enums;

 
public enum ArticleStatusEnum{

     
    PUBLISHED(1, "已发布"),

     
    DRAFT(0, "草稿"),

     
    RECYCLE(2, "回收站");

    private int value;
    private String desc;

    ArticleStatusEnum(Integer value, String desc) {
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
