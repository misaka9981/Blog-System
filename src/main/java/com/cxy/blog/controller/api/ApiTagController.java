package com.cxy.blog.controller.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.blog.common.util.Result;
import com.cxy.blog.common.util.ValidatorUtils;
import com.cxy.blog.model.Tag;
import com.cxy.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

 
@RestController
@RequestMapping("api/tag")
public class ApiTagController {
    @Autowired
    private TagService tagService;

    @GetMapping("list")
    public Result list(Page<Tag> page) {
        return Result.success("查询成功", tagService.page(page));
    }

    @GetMapping("all")
    public Result all() {
        return Result.success("查询成功", tagService.list());
    }

    @PostMapping
    public Result save(@RequestBody Tag tag) {
        ValidatorUtils.validate(tag);
        boolean res = tagService.save(tag);
        tagService.clearCache();
        return res ? Result.success("保存成功", tag) : Result.fail("保存失败");
    }

    @PutMapping
    public Result update(@RequestBody Tag tag) {
        ValidatorUtils.validate(tag);
        if (Objects.isNull(tag.getId())) {
            return Result.fail("ID不能为空");
        }
        boolean res = tagService.updateById(tag);
        tagService.clearCache();
        return res ? Result.success("修改成功", tag) : Result.fail("修改失败");
    }

    @GetMapping
    public Result info(Long id) {
        return Result.success("查询成功", tagService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id) {
        boolean res = tagService.removeById(id);
        tagService.clearCache();
        return res ? Result.success("删除成功") : Result.fail("删除失败");
    }
}
