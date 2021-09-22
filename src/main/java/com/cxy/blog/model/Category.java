package com.cxy.blog.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

 
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

     
    @NotBlank(message = "分类名不能为空")
    private String name;

     
    @NotBlank(message = "分类链接不能为空")
    private String url;
}
