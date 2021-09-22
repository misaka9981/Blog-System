package com.cxy.blog.controller.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.blog.common.util.Result;
import com.cxy.blog.common.util.ValidatorUtils;
import com.cxy.blog.model.Menu;
import com.cxy.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

 
@RestController
@RequestMapping("api/menu")
public class ApiMenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("list")
    public Result list(Page<Menu> page) {
        return Result.success("查询成功", menuService.page(page));
    }

    @PostMapping
    public Result save(@RequestBody Menu menu) {
        ValidatorUtils.validate(menu);
        menuService.save(menu);
        return Result.success("保存成功", menu);
    }

    @PutMapping
    public Result update(@RequestBody Menu menu) {
        ValidatorUtils.validate(menu);
        if (Objects.isNull(menu.getId())) {
            return Result.fail("ID不能为空");
        }
        menuService.updateById(menu);
        return Result.success("修改成功", menu);
    }

    @GetMapping
    public Result info(Long id) {
        return Result.success("查询成功", menuService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id) {
        return menuService.removeById(id) ? Result.success("删除成功") : Result.fail("删除失败");
    }
}
