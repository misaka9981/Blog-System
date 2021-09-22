package com.cxy.blog.controller.front;

import com.cxy.blog.common.constant.Const;
import com.cxy.blog.model.Article;
import com.cxy.blog.model.Comment;
import com.cxy.blog.model.Menu;
import com.cxy.blog.service.ArticleService;
import com.cxy.blog.service.CommentService;
import com.cxy.blog.service.ConfigService;
import com.cxy.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

 
public abstract class BaseController {

    @Autowired
    protected CommentService commentService;

    @Autowired
    protected ArticleService articleService;

    @Autowired
    protected MenuService menuService;

    @Autowired
    protected ConfigService configService;

    @ModelAttribute("latestComments")
    public List<Comment> latestComments() {
        return commentService.findLatestComments(8,false);
    }

    @ModelAttribute("randomArticles")
    public List<Article> randomArticles() {
        return articleService.findRandomArticles(8);
    }

    @ModelAttribute("MENUS")
    public List<Menu> menus(){
        return menuService.findAllMenu();
    }

    @ModelAttribute("GLOBAL")
    public Map<String,String> global(){
        return configService.findAllGlobal();
    }

     
    String pagination(int pageNum, int pageCount, String base){
        StringBuilder pageHtml=new StringBuilder("<ul class=\"pagination pagination-gal pull-right\">");
        if(pageNum>1){
                         pageHtml.append("<li class=\"prev\"><a href=\"").append(base).append(pageNum - 1).append("/\" title=\"上一页\">«</a></li>");
            if(pageNum> Const.PAGE_SIDE_NUM+1){
                                 pageHtml.append("<li><a href=\"").append(base).append("\" title=\"首页\">1</a></li>");
            }
            if(pageNum> Const.PAGE_SIDE_NUM+2){
                pageHtml.append("<li><span>...</span></li>");
            }
        }
                 int startNum=Math.max(pageNum- Const.PAGE_SIDE_NUM,1),endNum=Math.min(pageNum+ Const.PAGE_SIDE_NUM,pageCount);
        int diff=endNum-startNum,showPage=2*Const.PAGE_SIDE_NUM;
        if(pageCount>showPage && diff<showPage){
            if(startNum< Const.PAGE_SIDE_NUM){
                                 endNum=Math.min(endNum+diff,pageCount);
            }else{
                                 startNum=Math.max(startNum-diff,1);
            }
        }
                 for (int i = startNum; i <= endNum; i++) {
            if(i==pageNum){
                pageHtml.append("<li class=\"active disabled\"><span>").append(i).append("</span></li>");
            }else{
                pageHtml.append("<li><a href=\"").append(base).append(i).append("/\">").append(i).append("</a></li>");
            }
        }
        int temp=pageCount-pageNum;
        if(temp>0){
            if(temp> Const.PAGE_SIDE_NUM+1){
                pageHtml.append("<li><span>...</span></li>");
            }
            if(temp> Const.PAGE_SIDE_NUM){
                                 pageHtml.append("<li><a href=\"").append(base).append(pageCount).append("/\" title=\"最后一页\">").append(pageCount).append("</a></li>");
            }
                         pageHtml.append("<li class=\"next\"><a href=\"").append(base).append(pageNum + 1).append("/\" title=\"下一页\">»</a></li>");
        }
        pageHtml.append("</ul>");
        return pageHtml.toString();
    }
}
