package com.cxy.blog.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

 
@Data
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

     
    private String ip;

     
    private String city;

     
    private String url;

     
    private String referer;

     
    private String userAgent;

     
    private Date createTime;

     
    private Integer duration;

     
    private String type;

     
    private String params;

     
    private String result;

     
    private String method;

     
    private Boolean isNormal;

     
    private String browser;

     
    private String operatingSystem;
}
