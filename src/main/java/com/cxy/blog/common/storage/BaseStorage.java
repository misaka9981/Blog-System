package com.cxy.blog.common.storage;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

 
public abstract class BaseStorage {
     
    StorageConfig config;

    BaseStorage(StorageConfig config){
        this.config = config;
    }

     
    String getPath(String prefix, String suffix) {
                 LocalDateTime dateTime=LocalDateTime.now();
        String path = dateTime.getYear() + "/" + dateTime.getMonthValue()+"/" + dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        if(StringUtils.isNotBlank(prefix)){
            path = prefix.endsWith("/")?prefix + path:prefix + "/" + path;
        }
        return path + suffix;
    }

     
    public abstract String upload(byte[] data, String path);

     
    public abstract String uploadSuffix(byte[] data, String suffix);

     
    public abstract String upload(InputStream inputStream, String path);

     
    public abstract String uploadSuffix(InputStream inputStream, String suffix);

}
