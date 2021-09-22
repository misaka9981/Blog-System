package com.cxy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.blog.mapper.CategoryMapper;
import com.cxy.blog.model.Category;
import com.cxy.blog.service.CategoryService;
import org.springframework.stereotype.Service;

 
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
