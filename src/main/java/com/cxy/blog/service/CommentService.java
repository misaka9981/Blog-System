package com.cxy.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.blog.model.Comment;

import java.util.List;

 
public interface CommentService extends IService<Comment> {

     
    IPage<Comment> findPageByArticleId(Page<Comment> page, Long articleId);

     
    Integer countByArticleId(Long articleId);

     
    List<Comment> findLatestComments(Integer count, boolean showCheck);

     
    IPage<Comment> findCommentsByPage(Page<Comment> page, QueryWrapper wrapper);

     
    Comment findCommentById(Long id);

     
    void clearCache();
}
