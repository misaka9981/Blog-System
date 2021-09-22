package com.cxy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.blog.model.User;
import com.cxy.blog.common.util.Result;
import org.apache.commons.codec.DecoderException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

 
public interface UserService extends IService<User> {
     
    Result login(String username, String password) throws NoSuchAlgorithmException, DecoderException, InvalidKeySpecException;

     
    User findUserById(Long userId);

     
    User modifyUserById(User user);

     
    User findUserByUsername(String username);

     
    User findUserByEmail(String email);
}
