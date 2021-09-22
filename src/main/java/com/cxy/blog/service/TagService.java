package com.cxy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.blog.model.Tag;

import java.util.List;

 
public interface TagService extends IService<Tag> {
     
    List<Tag> findHotTags(Integer count);

     
    List<Tag> findTagsByArticleId(Long articleId);

     
    void clearCache();
}
