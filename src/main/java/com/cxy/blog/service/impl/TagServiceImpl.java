package com.cxy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.blog.mapper.TagMapper;
import com.cxy.blog.model.Tag;
import com.cxy.blog.service.TagService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

 
@Service
@CacheConfig(cacheNames = "tag")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    @Cacheable(key = "targetClass + methodName + #count")
    public List<Tag> findHotTags(Integer count) {
        return baseMapper.selectHotTags(count);
    }

    @Override
    public List<Tag> findTagsByArticleId(Long articleId) {
        return baseMapper.selectTagsByArticleId(articleId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void clearCache() {
    }
}
