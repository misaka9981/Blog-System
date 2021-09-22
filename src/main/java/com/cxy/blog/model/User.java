package com.cxy.blog.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

 
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
     
    @NotBlank(message = "用户名不能为空")
    private String username;

     
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

     
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码不能小于6位")
    private String password;

     
    private String salt;

     
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

     
    private Date lastLoginTime;

     
    private Date createTime;

     
    private Integer loginFailNum;

     
    private Boolean isDisable;

     
    private Boolean isAdmin;

     
    private String avatar;
}
