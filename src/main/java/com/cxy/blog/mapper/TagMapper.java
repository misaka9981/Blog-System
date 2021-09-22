package com.cxy.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.blog.model.Tag;
import org.apache.ibatis.annotations.Select;

import java.util.List;

 
public interface TagMapper extends BaseMapper<Tag> {
     
    List<Tag> selectTagsByArticleId(Long articleId);

     
    @Select("SELECT t1.* FROM blog_tag AS t1 LEFT JOIN blog_article_tag AS t2 ON t1.id = t2.tag_id LEFT JOIN blog_article AS t3 ON t2.article_id=t3.id ORDER BY t3.visits DESC LIMIT #{count}")
    List<Tag> selectHotTags(Integer count);
}
