package com.cxy.blog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.blog.common.constant.CodeEnum;
import com.cxy.blog.common.constant.Const;
import com.cxy.blog.common.exception.BlogException;
import com.cxy.blog.model.Article;
import com.cxy.blog.model.Category;
import com.cxy.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

 
@Controller
@RequestMapping("category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

     
    @GetMapping
    public String tags(Model model) {
        List<Category> categories=categoryService.list();
        model.addAttribute("list",categories);
        return "categories";
    }

     
    @GetMapping("{url}")
    public String category(Model model,@PathVariable(value = "url") String url) {
        return category(model,url,1);
    }

     
    @GetMapping("{url}/{pageIndex}")
    public String category(Model model,@PathVariable(value = "url") String url,@PathVariable(value = "pageIndex") Integer pageIndex) {
        Category category=categoryService.getOne(new QueryWrapper<Category>().eq("url",url));
        if(null==category){
            throw new BlogException(CodeEnum.NOT_FOUND.getValue(),"类别不存在："+url);
        }
        IPage<Article> page=articleService.findPageByCategory(new Page<>(pageIndex, Const.PAGE_SIZE),category.getId());
        model.addAttribute("info",category);
        model.addAttribute("page",page);
                 model.addAttribute("pageHtml",pagination((int)page.getCurrent(),(int)page.getPages(), "/category/"+url+"/"));
        return "list";
    }
}
