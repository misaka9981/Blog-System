package com.cxy.blog.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxy.blog.common.util.Result;
import com.cxy.blog.model.Log;
import com.cxy.blog.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

 
@RestController
@RequestMapping("api/log")
public class ApiLogController {
    @Autowired
    private LogService logService;

     
    @GetMapping("visitsCount")
    public Result visitsCount(){
        Map<String,Integer> data=new HashMap<>();
        int totalVisits= logService.count();
        data.put("totalVisits",totalVisits);
        int latestVisits=logService.count(new QueryWrapper<Log>().apply("create_time > DATE_SUB(CURDATE(), INTERVAL 1 WEEK)"));
        data.put("latestVisits",latestVisits);
        return Result.success("获取成功",data);
    }

     
    @GetMapping("latest")
    public Result latest(int number){
        List<Log> logList=logService.findLatestLog(number);
        return Result.success("获取成功",logList);
    }

     
    @GetMapping("browser")
    public Result browser(){
        return Result.success("获取成功",logService.statBrowser());
    }

     
    @GetMapping("operatingSystem")
    public Result operatingSystem(){
        return Result.success("获取成功",logService.statOperatingSystem());
    }

     
    @GetMapping("city")
    public Result city(){
        return Result.success("获取成功",logService.statCity());
    }
}
