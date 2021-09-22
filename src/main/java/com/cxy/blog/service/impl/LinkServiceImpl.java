package com.cxy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.blog.mapper.LinkMapper;
import com.cxy.blog.service.LinkService;
import com.cxy.blog.model.Link;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

 
@Service
@CacheConfig(cacheNames = "link")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    @Cacheable(key = "targetClass + methodName + #type")
    public List<Link> findLinkByType(Integer type) {
        return list(new QueryWrapper<Link>().eq("type",type));
    }

    @Override
    @CacheEvict(allEntries = true)
    public void clearCache() {
    }
}
