package com.cxy.blog.common.constant;

 
public enum StorageType {
     
    QINIU(1),
     
    ALIYUN(2),
     
    QCLOUD(3),
     
    LOCAL(4);

    private int value;

    StorageType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
