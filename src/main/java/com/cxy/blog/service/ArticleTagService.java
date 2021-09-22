package com.cxy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.blog.model.ArticleTag;

import java.util.List;

 
public interface ArticleTagService extends IService<ArticleTag> {
     
    boolean saveBatch(Long articleId, List<Long> tagIds);
}
