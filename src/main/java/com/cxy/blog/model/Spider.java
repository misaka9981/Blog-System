package com.cxy.blog.model;

 

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@TableName("blog_spider")
public class Spider implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
     
    @NotBlank(message = "爬虫名称不能为空")
    private String name;
     
    @NotBlank(message = "标题规则不能为空")
    private String titleRule;
     
    @NotBlank(message = "内容规则不能为空")
    private String contentRule;
}
