package com.cxy.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.blog.model.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

 
public interface ArticleMapper extends BaseMapper<Article> {
     
    Article selectArticleByUrl(String url);
     
    @Update("update blog_article set visits=visits+1 where id=#{id}")
    int updateForVisitsById(Long id);
     
    List<Article> selectTopArticles();
     
    Page<Article> selectArticleList(Page page, @Param("keyword")String keyword);
     
    Page<Article> selectListByTag(Page page, @Param("tagId")Long tagId);
     
    Page<Article> selectListByCategory(Page page, @Param("categoryId")Long categoryId);

     
    @Select("select id,url,title,visits from blog_article where status=1 and type=0 order by visits desc limit #{count}")
    List<Article> selectHotArticles(Integer count);

     
    @Select("SELECT t1.id,t1.url,t1.title,t1.visits FROM blog_article AS t1 JOIN (SELECT ROUND(RAND() * (SELECT MAX(id) FROM blog_article)) AS id) AS t2 WHERE t1.id >= t2.id and t1.status=1 and t1.type=0 ORDER BY t1.id ASC LIMIT #{count}")
    List<Article> selectRandomArticles(Integer count);

     
    @Select("select id,url,title from blog_article where `status`=1 and `type`=0 and id<#{id} limit 1")
    Article selectPreviousById(Long id);

     
    @Select("select id,url,title from blog_article where `status`=1 and `type`=0 and id>#{id} limit 1")
    Article selectNextById(Long id);

     
    @Select("select * from blog_article where `status`=1 and `type`=0 order by id desc limit #{number}")
    List<Article> selectLatestArticle(int number);
}
