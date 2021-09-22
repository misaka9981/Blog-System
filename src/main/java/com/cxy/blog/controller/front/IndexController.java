package com.cxy.blog.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.blog.common.annotation.CurrentUser;
import com.cxy.blog.common.constant.CodeEnum;
import com.cxy.blog.common.constant.Const;
import com.cxy.blog.common.exception.BlogException;
import com.cxy.blog.model.Article;
import com.cxy.blog.model.Comment;
import com.cxy.blog.model.User;
import com.cxy.blog.model.enums.ArticleTypeEnum;
import com.cxy.blog.model.enums.LinkTypeEnum;
import com.cxy.blog.service.LinkService;
import com.cxy.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

 
@Controller
public class IndexController extends BaseController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private TagService tagService;

     
    @GetMapping("/")
    public String index(Model model) {
        return index(model, 1);
    }

     
    @GetMapping("/page/{pageIndex}")
    public String index(Model model, @PathVariable(value = "pageIndex") Integer pageIndex) {
        if (pageIndex == 1) {
            List<Article> topArticles = articleService.findAllTopArticles();
            model.addAttribute("top", topArticles);
        }
        IPage<Article> page = articleService.findPageByKeyword(new Page<>(pageIndex, Const.PAGE_SIZE), null);
        model.addAttribute("page", page);
                 List<Article> hotArticles = articleService.findHotArticles(Const.HOT_ARTICLE_SIZE);
        model.addAttribute("hotArticles", hotArticles);
                 model.addAttribute("hotTags", tagService.findHotTags(Const.HOT_TAG_SIZE));
                 model.addAttribute("friendLinks", linkService.findLinkByType(LinkTypeEnum.FRIEND_LINK.getValue()));
                 model.addAttribute("personalLinks", linkService.findLinkByType(LinkTypeEnum.PERSONAL_LINK.getValue()));
                 model.addAttribute("pageHtml", pagination((int) page.getCurrent(), (int) page.getPages(), "/page/"));
        return "index";
    }

     
    @GetMapping("{url}.html")
    public String index(Model model, @PathVariable(value = "url") String url) {
        Article info = articleService.findArticleByUrl(url);
        if (null == info) {
            throw new BlogException(CodeEnum.NOT_FOUND.getValue(), "文章不存在：" + url);
        }
        articleService.updateForVisitsById(info.getId());
        model.addAttribute("info", info);
                 if (info.getIsComment()) {
            IPage<Comment> commentPage = commentService.findPageByArticleId(new Page<>(1, Const.COMMENT_SIZE), info.getId());
            model.addAttribute("comments", commentPage);
            Integer commentCount = commentService.countByArticleId(info.getId());
            model.addAttribute("commentCount", commentCount);
        }
                 Article previousArticle = articleService.findPreviousById(info.getId());
        model.addAttribute("previousArticle", previousArticle);
        Article nextArticle = articleService.findNextById(info.getId());
        model.addAttribute("nextArticle", nextArticle);
                 if (ArticleTypeEnum.CUSTOM.getValue().equals(info.getType())) {
            return "custom";
        }
        return "article";
    }

     
    @GetMapping("/login")
    public String login(@CurrentUser User currentUser) {
        return null == currentUser ? "login" : "/";
    }

     
    @GetMapping(value = "/register")
    public String register() {
        return "register";
    }
}
