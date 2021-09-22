package com.cxy.blog.model.enums;

 
public enum ConfigTypeEnum {
     
    GLOBAL_OPTION(1,"全局变量"),

     
    SYSTEM_CONFIG(2,"系统配置");

    private int value;
    private String desc;

    ConfigTypeEnum(Integer value, String desc) {
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
