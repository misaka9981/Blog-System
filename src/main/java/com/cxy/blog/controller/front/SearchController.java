package com.cxy.blog.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.blog.common.constant.Const;
import com.cxy.blog.model.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

 
@Controller
@RequestMapping("search")
public class SearchController extends BaseController {

     
    @GetMapping("{keyword}")
    public String search(Model model,@PathVariable(value = "keyword") String keyword) {
        return search(model,keyword,1);
    }

     
    @GetMapping("{keyword}/{pageIndex}")
    public String search(Model model,@PathVariable(value = "keyword") String keyword,@PathVariable(value = "pageIndex") Integer pageIndex) {
        IPage<Article> page=articleService.findPageByKeyword(new Page<>(pageIndex, Const.PAGE_SIZE),keyword);
        model.addAttribute("page",page);
        return "search";
    }
}
