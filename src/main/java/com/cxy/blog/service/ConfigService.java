package com.cxy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.blog.model.Config;

import java.util.Map;

 
public interface ConfigService extends IService<Config> {
     
    Map<String, String> findAllGlobal();

     
    void clearCache();

     
    String findByName(String name);

     
    <T> T getConfigObject(String name, Class<T> clazz);
}
