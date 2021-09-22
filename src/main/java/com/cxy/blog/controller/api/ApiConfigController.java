package com.cxy.blog.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.blog.common.constant.ConfigConst;
import com.cxy.blog.common.util.Result;
import com.cxy.blog.common.util.ValidatorUtils;
import com.cxy.blog.model.Config;
import com.cxy.blog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

 
@RestController
@RequestMapping("api/config")
public class ApiConfigController {
    @Autowired
    private ConfigService configService;

    @GetMapping("list")
    public Result list(Page<Config> page) {
        return Result.success("查询成功", configService.page(page));
    }

    @PostMapping
    public Result save(@RequestBody Config config) {
        ValidatorUtils.validate(config);
        if (config.getName().equals(ConfigConst.FILE_STORAGE)) {
            ValidatorUtils.validateStorageConfig(config.getValue());
        }
        boolean result = configService.save(config);
        if (result) {
            configService.clearCache();
            return Result.success("保存成功");
        } else {
            return Result.fail("保存失败");
        }
    }


    @PutMapping
    public Result update(@RequestBody Config config) {
        ValidatorUtils.validate(config);
        if (Objects.isNull(config.getId())) {
            return Result.fail("ID不能为空");
        }
        if (config.getName().equals(ConfigConst.FILE_STORAGE)) {
            ValidatorUtils.validateStorageConfig(config.getValue());
        }
        boolean result = configService.updateById(config);
        if (result) {
            configService.clearCache();
            return Result.success("修改成功");
        } else {
            return Result.fail("修改失败");
        }
    }

    @GetMapping
    public Result info(Long id) {
        return Result.success("查询成功", configService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id) {
        boolean result = configService.removeById(id);
        if (result) {
            configService.clearCache();
            return Result.success("删除成功");
        } else {
            return Result.success("删除失败");
        }
    }

    @GetMapping("global")
    public Result global() {
        return Result.success("查询成功", configService.findAllGlobal());
    }
}
