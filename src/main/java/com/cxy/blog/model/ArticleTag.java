package com.cxy.blog.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

 
@Data
@NoArgsConstructor
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

     
    private Long articleId;

     
    private Long tagId;

    public ArticleTag(Long articleId, Long tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }
}
