package com.cxy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.blog.mapper.CommentMapper;
import com.cxy.blog.model.Article;
import com.cxy.blog.model.Comment;
import com.cxy.blog.model.User;
import com.cxy.blog.model.enums.CommentStatusEnum;
import com.cxy.blog.model.enums.CommentTargetTypeEnum;
import com.cxy.blog.service.ArticleService;
import com.cxy.blog.service.CommentService;
import com.cxy.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toSet;

 
@Service
@CacheConfig(cacheNames = "comment")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Override
    @Cacheable(key = "targetClass + methodName + #p0.current +#p0.size + #p1")
    public IPage<Comment> findPageByArticleId(Page<Comment> page, Long articleId) {
        IPage<Comment> commentPage = baseMapper.selectPage(page, new QueryWrapper<Comment>()
                .eq("target_type", CommentTargetTypeEnum.ARTICLE.getValue())
                .eq("article_id", articleId)
                .eq("status", CommentStatusEnum.PUBLISHED.getValue())
                .orderByDesc("id"));
                 List<Comment> list = commentPage.getRecords();
        Set<Long> commentIds = list.stream().map(Comment::getId).collect(toSet());
        List<Comment> subComments = baseMapper.selectList(new QueryWrapper<Comment>()
                .eq("target_type", CommentTargetTypeEnum.COMMENT.getValue())
                .eq("status", CommentStatusEnum.PUBLISHED.getValue())
                .in("parent_id", commentIds));
                 Map<Long, List<Comment>> subCommentsMap = new HashMap<>();
        for (Comment subComment : subComments) {
            subCommentsMap.compute(subComment.getParentId(), (k, v) -> {
                if (v == null) {
                    v = new ArrayList<>();
                }
                packageComment(subComment);
                v.add(subComment);
                return v;
            });
        }

        for (Comment comment : list) {
            packageComment(comment);
            comment.setSubComments(subCommentsMap.get(comment.getId()));
        }
        return commentPage;
    }

    @Override
    public Integer countByArticleId(Long articleId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        return baseMapper.selectCount(queryWrapper);
    }

     
    private void packageComment(Comment comment) {
        Long userId = comment.getUserId();
        User user = userService.findUserById(userId);
        comment.setUser(user);
        Long replyUserId = comment.getReplyUserId();
        if (Objects.nonNull(replyUserId)) {
            User replyUser = userService.findUserById(replyUserId);
            comment.setReplyUser(replyUser);
        }
        Article article = articleService.findArticleById(comment.getArticleId());
        comment.setArticle(article);
    }

    @Override
    @Cacheable(key = "targetClass + methodName + #p0 + #p1")
    public List<Comment> findLatestComments(Integer count, boolean showCheck) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        if (showCheck) {
            wrapper.in("status", CommentStatusEnum.CHECKING.getValue(), CommentStatusEnum.PUBLISHED.getValue());
        } else {
            wrapper.eq("status", CommentStatusEnum.PUBLISHED.getValue());
        }
        wrapper.orderByDesc("id");
        wrapper.last(" LIMIT " + count);
        List<Comment> list = baseMapper.selectList(wrapper);
        list.forEach(this::packageComment);
        return list;
    }

    @Override
    public IPage<Comment> findCommentsByPage(Page<Comment> page, QueryWrapper wrapper) {
        IPage<Comment> iPage = baseMapper.selectPage(page, wrapper);
        if (iPage.getTotal() > 0) {
            iPage.getRecords().forEach(comment -> packageComment(comment));
        }
        return iPage;
    }

    @Override
    public Comment findCommentById(Long id) {
        Comment comment = baseMapper.selectById(id);
        packageComment(comment);
        return comment;
    }

    @Override
    @CacheEvict(allEntries = true)
    public void clearCache() {
    }
}
