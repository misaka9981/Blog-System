package com.cxy.blog.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

 
@Data
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

     
    @NotBlank(message = "参数名不能为空")
    private String name;

     
    private String value;

     
    private Integer type;

     
    private String description;
}
