package com.cxy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.blog.mapper.MenuMapper;
import com.cxy.blog.model.Menu;
import com.cxy.blog.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

 
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> findAllMenu() {
        return list(new QueryWrapper<Menu>().orderByAsc("sort"));
    }
}
