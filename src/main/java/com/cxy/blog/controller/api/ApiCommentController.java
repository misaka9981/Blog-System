package com.cxy.blog.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.blog.common.annotation.CurrentUser;
import com.cxy.blog.common.constant.CodeEnum;
import com.cxy.blog.common.constant.ConfigConst;
import com.cxy.blog.common.util.IPUtils;
import com.cxy.blog.common.util.Result;
import com.cxy.blog.common.util.ValidatorUtils;
import com.cxy.blog.model.Comment;
import com.cxy.blog.model.User;
import com.cxy.blog.model.enums.CommentStatusEnum;
import com.cxy.blog.model.enums.CommentTargetTypeEnum;
import com.cxy.blog.service.CommentService;
import com.cxy.blog.service.ConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

 
@RestController
@RequestMapping("api/comment")
public class ApiCommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ConfigService configService;

    @GetMapping("list")
    public Result list(Page<Comment> page, Comment comment) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>(comment);
        String content = comment.getContent();
        comment.setContent(null);
        if (StringUtils.isNotBlank(content)) {
            queryWrapper.like("content", content);
        }
        if (Objects.isNull(comment.getStatus())) {
            queryWrapper.ne("status", 2);
        }
        IPage<Comment> commentPage = commentService.findCommentsByPage(page, queryWrapper);
        return Result.success("查询成功", commentPage);
    }

     
    @GetMapping("more")
    public List<Comment> commentPage(int current, int size, long articleId) {
        return commentService.findPageByArticleId(new Page<>(current, size, false), articleId).getRecords();
    }

    @PostMapping(value = {"", "save"})
    public Result save(@RequestBody Comment comment, HttpServletRequest request, @CurrentUser User currentUser) {
        ValidatorUtils.validate(comment);
        if (comment.getTargetType().equals(CommentTargetTypeEnum.COMMENT.getValue())) {
            if (Objects.isNull(comment.getParentId())) {
                return new Result(CodeEnum.VALIDATION_ERROR.getValue(), "父级评论不能为空");
            }
            if (Objects.isNull(comment.getReplyUserId())) {
                return new Result(CodeEnum.VALIDATION_ERROR.getValue(), "回复的人不能为空");
            }
        }
         comment.setUserId(currentUser.getId());
        if (currentUser.getIsAdmin()) {
            comment.setStatus(CommentStatusEnum.PUBLISHED.getValue());
        } else {
            Boolean isCheck = configService.getConfigObject(ConfigConst.COMMENT_CHECK, Boolean.class);
            comment.setStatus(isCheck ? CommentStatusEnum.CHECKING.getValue() : CommentStatusEnum.PUBLISHED.getValue());
        }
        comment.setUserAgent(request.getHeader("user-agent"));
        comment.setIp(IPUtils.getIpAddr(request));
        commentService.save(comment);
        commentService.clearCache();
        return Result.success("添加成功", comment);
    }

    @PutMapping
    public Result update(@RequestBody Comment comment) {
        if (Objects.isNull(comment.getId())) {
            return new Result(CodeEnum.VALIDATION_ERROR.getValue(), "评论ID不能为空");
        }
        boolean res = commentService.updateById(comment);
        commentService.clearCache();
        return res ? Result.success("修改成功") : Result.fail("修改失败");
    }

    @GetMapping
    public Result info(Long id) {
        return Result.success("查询成功", commentService.findCommentById(id));
    }

     
    @GetMapping("commentCount")
    public Result commentCount() {
        Map<String, Integer> data = new HashMap<>();
        int totalComment = commentService.count();
        data.put("totalComment", totalComment);
        int latestComment = commentService.count(new QueryWrapper<Comment>().apply("create_time > DATE_SUB(CURDATE(), INTERVAL 1 WEEK)"));
        data.put("latestComment", latestComment);
        return Result.success("获取成功", data);
    }

     
    @GetMapping("latest")
    public Result latest(int number) {
        List<Comment> commentList = commentService.findLatestComments(number, true);
        return Result.success("查询成功", commentList);
    }
}
