package com.cxy.blog.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

 
@Data
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

     
    @NotBlank(message = "标签名不能为空")
    private String name;

     
    @NotBlank(message = "标签链接不能为空")
    private String url;
}
