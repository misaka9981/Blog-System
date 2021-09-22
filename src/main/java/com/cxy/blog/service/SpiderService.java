package com.cxy.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.blog.model.Article;
import com.cxy.blog.model.Spider;

import java.io.IOException;

 
public interface SpiderService extends IService<Spider> {
     
    Article spiderArticle(Spider spider,String url) throws IOException;
}
