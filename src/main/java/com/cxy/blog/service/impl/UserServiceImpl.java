package com.cxy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.blog.common.constant.CodeEnum;
import com.cxy.blog.common.constant.Const;
import com.cxy.blog.common.util.PBKDF2Utils;
import com.cxy.blog.common.util.Result;
import com.cxy.blog.mapper.UserMapper;
import com.cxy.blog.model.User;
import com.cxy.blog.service.UserService;
import org.apache.commons.codec.DecoderException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

 
@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public Result login(String username, String password) throws NoSuchAlgorithmException, DecoderException, InvalidKeySpecException {
        Result result = Result.fail();
                 User user = getOne(new QueryWrapper<User>().eq("username", username).or().eq("email", username));
        if (null == user) {
            result.setMsg("账号不存在");
            return result;
        }
        if (user.getIsDisable()) {
            result.setMsg("账号已被禁用");
            return result;
        }
                 boolean res = PBKDF2Utils.verify(password, user.getSalt(), user.getPassword());
                 if (res) {
            result.setCode(CodeEnum.SUCCESS.getValue());
            result.setMsg("登录成功");
            result.setData(user);
                         user.setLoginFailNum(0);
                         user.setLastLoginTime(new Date());
        } else {
                         Integer loginFail = user.getLoginFailNum() + 1;
            user.setLoginFailNum(loginFail);
                         if (loginFail >= Const.LOGIN_FAIL_COUNT) {
                user.setIsDisable(true);
                result.setMsg("密码错误，可尝试登陆次数已达上限，账号已禁用！");
            } else {
                result.setMsg(String.format("密码错误，连续输错密码%d次后将禁用账号，您已经输错了%d次。", Const.LOGIN_FAIL_COUNT, loginFail));
            }
        }
        updateById(user);
        return result;
    }

    @Override
    @Cacheable(key = "#userId")
    public User findUserById(Long userId) {
        return baseMapper.selectById(userId);
    }

    @Override
    public User modifyUserById(User user) {
        updateById(user);
        return baseMapper.selectById(user.getId());
    }

    @Override
    public User findUserByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public User findUserByEmail(String email) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("email", email));
    }

    @Override
    public boolean save(User user) {
        return super.save(user);
    }
}
