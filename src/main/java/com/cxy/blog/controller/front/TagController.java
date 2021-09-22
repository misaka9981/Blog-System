package com.cxy.blog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.blog.common.constant.CodeEnum;
import com.cxy.blog.common.constant.Const;
import com.cxy.blog.common.exception.BlogException;
import com.cxy.blog.model.Article;
import com.cxy.blog.model.Tag;
import com.cxy.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

 
@Controller
@RequestMapping("tag")
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

     
    @GetMapping("/")
    public String tags(Model model) {
        List<Tag> tags=tagService.list();
        model.addAttribute("list",tags);
        return "tags";
    }

     
    @GetMapping("{url}")
    public String tag(Model model,@PathVariable(value = "url") String url) {
        return tag(model,url,1);
    }

     
    @GetMapping("{url}/{pageIndex}")
    public String tag(Model model,@PathVariable(value = "url") String url,@PathVariable(value = "pageIndex") Integer pageIndex) {
        Tag tag=tagService.getOne(new QueryWrapper<Tag>().eq("url",url));
        if(null==tag){
            throw new BlogException(CodeEnum.NOT_FOUND.getValue(),"标签不存在："+url);
        }
        IPage<Article> page=articleService.findPageByTag(new Page<>(pageIndex, Const.PAGE_SIZE),tag.getId());
        model.addAttribute("info",tag);
        model.addAttribute("page",page);
                 model.addAttribute("pageHtml",pagination((int)page.getCurrent(),(int)page.getPages(), "/tag/"+url+"/"));
        return "list";
    }
}
