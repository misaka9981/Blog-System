package com.cxy.blog.model.enums;

 
public enum ArticleTypeEnum {

     
    ORDINARY(0, "普通文章"),

     
    CUSTOM(1, "自定义");

    private Integer value;
    private String desc;

    ArticleTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
