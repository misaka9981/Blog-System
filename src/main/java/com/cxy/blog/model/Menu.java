package com.cxy.blog.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

 
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

     
    @NotBlank(message = "菜单名不能为空")
    private String name;

     
    @NotBlank(message = "菜单链接不能为空")
    private String url;

     
    private Boolean isBlank;

     
    private String icon;

     
    @NotNull(message = "菜单排序不能为空")
    private Integer sort;
}
