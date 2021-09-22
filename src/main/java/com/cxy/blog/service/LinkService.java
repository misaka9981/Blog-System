package com.cxy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.blog.model.Link;

import java.util.List;

 
public interface LinkService extends IService<Link> {
     
    List<Link> findLinkByType(Integer type);

     
    void clearCache();
}
