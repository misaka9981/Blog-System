package com.cxy.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.blog.model.Article;

import java.util.List;

 
public interface ArticleService extends IService<Article> {
     
    Article findArticleByUrl(String url);

     
    void updateForVisitsById(Long articleId);

     
    List<Article> findAllTopArticles();

     
    IPage<Article> findPageByKeyword(Page<Article> page, String keyword);

     
    IPage<Article> findPageByTag(Page<Article> page, Long tagId);

     
    IPage<Article> findPageByCategory(Page<Article> page, Long categoryId);

     
    List<Article> findHotArticles(Integer count);

     
    List<Article> findRandomArticles(Integer count);

     
    Article findPreviousById(Long id);

     
    Article findNextById(Long id);

     
    List<Article> findLatestArticle(int number);

     
    void clearCache();

     
    Article findArticleById(Long articleId);
}
