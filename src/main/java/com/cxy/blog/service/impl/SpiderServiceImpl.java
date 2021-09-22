package com.cxy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.blog.common.constant.Const;
import com.cxy.blog.common.storage.OSSFactory;
import com.cxy.blog.mapper.SpiderMapper;
import com.cxy.blog.model.Article;
import com.cxy.blog.model.Spider;
import com.cxy.blog.service.ConfigService;
import com.cxy.blog.service.SpiderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Iterator;

 
@Slf4j
@Service
public class SpiderServiceImpl extends ServiceImpl<SpiderMapper, Spider> implements SpiderService {
    @Autowired
    private ConfigService configService;

    @Override
    public Article spiderArticle(Spider spider, String url) throws IOException {
        Article article = new Article();
        Document doc = Jsoup.connect(url).userAgent(Const.USER_AGENT).timeout(Const.SPIDER_TIMEOUT).get();
                 String title = doc.select(spider.getTitleRule()).text();
        if (StringUtils.isEmpty(title)){
            return null;
        }
        article.setTitle(title);
                 String description=doc.head().select("meta[name=description]").attr("content");
        article.setDescription(description);
                 Element detail = doc.select(spider.getContentRule()).first();
        if (detail == null){
            return null;
        }
        downImage(detail);
                 String content=detail.html().replaceAll("<a(.*?)>", "").replaceAll("</a>", "");
        article.setContent(content);
        article.setContentMd(content);
        return article;
    }

     
    private Element downImage(Element content) {
                 Iterator<Element> iterator = content.getElementsByTag("img").iterator();
        while (iterator.hasNext()) {
            Element image=iterator.next();
            String url = image.attr("abs:src");
            InputStream inputStream = null;
                         String suffix ="."+StringUtils.substringAfterLast(url,".");
                         try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestProperty("User-Agent", Const.USER_AGENT);
                connection.setConnectTimeout(Const.SPIDER_TIMEOUT);
                inputStream = connection.getInputStream();
                String imageUrl = OSSFactory.build().uploadSuffix(inputStream, suffix);
                image.attr("src", imageUrl);
            } catch (Exception e) {
                log.error("图片下载失败："+url,e);
                                 image.remove();
            } finally {
                try {
                    if (inputStream != null){
                        inputStream.close();
                    }
                } catch (IOException e) {
                    log.error("关闭输入流时出现异常",e);
                }
            }
        }
        return content;
    }
}
