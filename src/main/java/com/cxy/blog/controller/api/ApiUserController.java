package com.cxy.blog.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.blog.common.annotation.CurrentUser;
import com.cxy.blog.common.constant.CodeEnum;
import com.cxy.blog.common.constant.Const;
import com.cxy.blog.common.util.PBKDF2Utils;
import com.cxy.blog.common.util.Result;
import com.cxy.blog.common.util.ValidatorUtils;
import com.cxy.blog.model.User;
import com.cxy.blog.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

 
@RestController
@RequestMapping("api/user")
public class ApiUserController {
    @Autowired
    private UserService userService;

    @GetMapping("list")
    public Result list(Page<User> page, User user) {
        String nickname = user.getNickname();
        user.setNickname(null);
        String email = user.getEmail();
        user.setEmail(null);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        if (StringUtils.isNotBlank(nickname)) {
            queryWrapper.like("nickname", nickname);
        }
        if (StringUtils.isNotBlank(email)) {
            queryWrapper.like("email", email);
        }
        IPage<User> userPage = userService.page(page, queryWrapper);
        return Result.success("查询成功", userPage);
    }

     
    @PostMapping
    public Result save(@RequestBody User user) throws Exception {
        ValidatorUtils.validate(user);
        if (null != userService.findUserByUsername(user.getUsername())) {
            return Result.fail("用户名已被注册");
        }
        if (null != userService.findUserByEmail(user.getEmail())) {
            return Result.fail("邮箱已被注册");
        }
                 String salt = PBKDF2Utils.getSalt();
                 String password = PBKDF2Utils.getPBKDF2(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);
        user.setLoginFailNum(0);
        user.setCreateTime(new Date());
        boolean flag = userService.save(user);
        if (!flag) {
            return Result.fail("注册失败");
        }
        return Result.success("注册成功");
    }

    @PutMapping
    public Result update(@RequestBody User user, HttpSession session, @CurrentUser User currentUser) {
        if (Objects.isNull(user.getId())) {
            return new Result(CodeEnum.VALIDATION_ERROR.getValue(), "用户ID不能为空");
        }
         if (currentUser.getId().equals(user.getId())) {
            if (Objects.nonNull(user.getIsDisable()) && user.getIsDisable()) {
                return new Result(CodeEnum.VALIDATION_ERROR.getValue(), "不可以禁用自己");
            } else if (Objects.nonNull(user.getIsAdmin()) && !user.getIsAdmin()) {
                return new Result(CodeEnum.VALIDATION_ERROR.getValue(), "不可以将自己降为普通用户");
            }
        }
                 user.setPassword(null);
        user.setSalt(null);
        user = userService.modifyUserById(user);
        if (currentUser.getId().equals(user.getId())) {
            currentUser = userService.findUserById(currentUser.getId());
            session.setAttribute(Const.USER_SESSION_KEY, currentUser);
        }
        return Result.success("修改成功", user);
    }

    @GetMapping
    public Result info(Long id, @CurrentUser User currentUser) {
        User user = null;
        if (Objects.isNull(id)) {
            user = currentUser;
        } else {
            user = userService.findUserById(id);
        }
        return Result.success("获取成功", user);
    }

    @PostMapping("changePass")
    public Result changePass(String oldPass, String newPass, HttpSession session, @CurrentUser User currentUser) throws Exception {
        ValidatorUtils.isBlank(oldPass, "原密码不能为空");
        ValidatorUtils.isBlank(newPass, "新密码不能为空");
         boolean checkPass = PBKDF2Utils.verify(oldPass, currentUser.getSalt(), currentUser.getPassword());
        if (!checkPass) {
            return Result.fail("原密码输入错误");
        }
                 String salt = PBKDF2Utils.getSalt();
                 String password = PBKDF2Utils.getPBKDF2(newPass, salt);
        currentUser.setSalt(salt);
        currentUser.setPassword(password);
        userService.modifyUserById(currentUser);
                 session.removeAttribute(Const.USER_SESSION_KEY);
        return Result.success("修改密码成功");
    }
}
