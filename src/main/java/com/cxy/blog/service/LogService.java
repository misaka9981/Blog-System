package com.cxy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.blog.model.Log;

import java.util.List;
import java.util.Map;

 
public interface LogService extends IService<Log> {

     
    List<Log> findLatestLog(int number);

     
    Map<String,Integer> statBrowser();

     
    Map<String,Integer> statOperatingSystem();

     
    List<Map<String,Integer>> statCity();

     
    void addLog(Log log);
}
