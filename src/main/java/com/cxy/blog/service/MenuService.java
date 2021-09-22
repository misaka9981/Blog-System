package com.cxy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.blog.model.Menu;

import java.util.List;

 
public interface MenuService extends IService<Menu> {
     
    List<Menu> findAllMenu();
}
