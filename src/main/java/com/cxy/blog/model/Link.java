package com.cxy.blog.model;

import lombok.Data;

import java.io.Serializable;

 
@Data
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

     
    private String name;

     
    private String url;

     
    private Integer type;

}
