package com.cxy.blog.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.blog.common.constant.CodeEnum;
import com.cxy.blog.common.exception.BlogException;
import com.cxy.blog.common.util.Result;
import com.cxy.blog.common.util.ValidatorUtils;
import com.cxy.blog.model.Article;
import com.cxy.blog.model.Tag;
import com.cxy.blog.model.enums.ArticleStatusEnum;
import com.cxy.blog.service.ArticleService;
import com.cxy.blog.service.ArticleTagService;
import com.cxy.blog.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

 
@RestController
@RequestMapping("api/article")
public class ApiArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleTagService articleTagService;

    @GetMapping("list")
    public Result list(Page<Article> page, Article article) {
                 String title = article.getTitle();
        article.setTitle(null);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>(article);
        if (article.getStatus() == null) {
            queryWrapper.in("status", ArticleStatusEnum.DRAFT.getValue(), ArticleStatusEnum.PUBLISHED.getValue());
        }
        if (StringUtils.isNotBlank(title)) {
            queryWrapper.like(true, "title", title);
        }
        IPage<Article> articlePage = articleService.page(page, queryWrapper);
        articlePage.getRecords().forEach(post -> post.setTags(tagService.findTagsByArticleId(post.getId())));
        return Result.success("查询成功", articlePage);
    }

    @PostMapping
    public Result save(@RequestBody Article article) {
        ValidatorUtils.validate(article);
        if (StringUtils.isBlank(article.getUrl())) {
            article.setUrl(article.getTitle());
        }
        article.setUpdateTime(new Date());
        boolean res = articleService.saveOrUpdate(article);
        if (!res) {
            return Result.fail("保存失败");
        }
        if (!CollectionUtils.isEmpty(article.getTags())) {
            Long articleId = article.getId() != null ? article.getId() : articleService.findArticleByUrl(article.getUrl()).getId();
            List<Long> tagIds = article.getTags().stream().map(Tag::getId).collect(Collectors.toList());
            res = articleTagService.saveBatch(articleId, tagIds);
            if (!res) {
                return Result.fail("文章已成功保存，但关联标签保存失败");
            }
        }
        articleService.clearCache();
        return Result.success("保存成功", article);
    }

     
    @PostMapping("modify")
    public Result modify(@RequestBody Article article) {
        if (article.getId() == null) {
            throw new BlogException(CodeEnum.VALIDATION_ERROR.getValue(), "修改数据时ID不能为空");
        }
        article.setUpdateTime(new Date());
        boolean res = articleService.updateById(article);
        if (!res) {
            return Result.fail("保存失败");
        }
        articleService.clearCache();
        return Result.success("保存成功", article);
    }

    @GetMapping
    public Result info(Long id) {
        Article article = articleService.getById(id);
        article.setTags(tagService.findTagsByArticleId(article.getId()));
        return Result.success("查询成功", article);
    }

    @DeleteMapping
    public Result remove(Long[] ids) {
        boolean res = articleService.removeByIds(Arrays.asList(ids));
        articleService.clearCache();
        return res ? Result.success("删除成功") : Result.fail("删除失败");
    }

     
    @GetMapping("latest")
    public Result latest(int number) {
        List<Article> articleList = articleService.findLatestArticle(number);
        return Result.success("查询成功", articleList);
    }

}
