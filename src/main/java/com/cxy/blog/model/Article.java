package com.cxy.blog.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

 
@Data
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
     
    private String url;

     
    @NotBlank(message = "文章标题不能为空")
    private String title;

     
    private Long categoryId;

     
    private String description;

     
    @NotBlank(message = "文章内容不能为空")
    private String content;

     
    private Boolean isOriginal;

     
    private String sourceUrl;

     
    private String contentMd;

     
    private Date createTime;

     
    @TableField(update = "now()")
    private Date updateTime;

     
    private String image;

     
    private Boolean isTop;

     
    private Boolean isComment;

     
    private Integer visits;

     
    private Integer status;

     
    private Integer type;

     
    @TableField(exist = false)
    private List<Tag> tags;

     
    @TableField(exist = false)
    private Category category;

     
    public String getKeywords() {
        if (tags == null || tags.isEmpty()) {
            return null;
        }
        List<String> list = tags.stream().map(Tag::getName).collect(Collectors.toList());
        return String.join(",", list);
    }
}
