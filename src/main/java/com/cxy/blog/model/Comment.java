package com.cxy.blog.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

 
@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

     
    @NotNull(message = "评论目标类型不能为空")
    private Integer targetType;

     
    @NotNull(message = "文章ID不能为空")
    private Long articleId;

     
    private Long userId;

     
    private Long replyUserId;

     
    @NotBlank(message = "评论内容不能为空")
    private String content;

     
    private String userAgent;

     
    private String ip;

     
    private Long parentId;

     
    private Date createTime;

     
    private Integer status;

     
    @TableField(exist = false)
    private Article article;

     
    @TableField(exist = false)
    private List<Comment> subComments;

     
    @TableField(exist = false)
    private User user;

     
    @TableField(exist = false)
    private User replyUser;
}
