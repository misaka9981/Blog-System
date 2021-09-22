package com.cxy.blog.model.enums;

 
public enum LinkTypeEnum {
     
    FRIEND_LINK(1,"友情链接"),
     
    PERSONAL_LINK(2,"个人链接");


    private int value;
    private String desc;

    LinkTypeEnum(Integer value, String desc) {
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
