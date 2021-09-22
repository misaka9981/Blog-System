package com.cxy.blog.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum CommentTargetTypeEnum {

     
    ARTICLE(1, "文章"),

     
    COMMENT(2, "评论");

    @EnumValue
    private int value;
    private String desc;

    CommentTargetTypeEnum(Integer value, String desc) {
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
