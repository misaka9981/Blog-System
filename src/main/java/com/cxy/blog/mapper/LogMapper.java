package com.cxy.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.blog.model.Log;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

 
public interface LogMapper extends BaseMapper<Log> {
     
    @Select("select * from blog_log order by create_time desc limit #{number}")
    List<Log> selectLatestLog(int number);

     
    @Select("select user_agent from blog_log")
    List<String> selectAllUserAgent();

     
    @Select("SELECT city,COUNT(city) count FROM blog_log GROUP BY city ORDER BY count DESC LIMIT 10")
    List<Map<String,Integer>> selectCityCount();

     
    @Select("select browser from blog_log")
    List<String> selectAllBrowser();

     
    @Select("select operating_system from blog_log")
    List<String> selectAllOperatingSystem();
}
