package com.cxy.blog.common.storage;


import com.cxy.blog.common.util.SpringUtils;
import com.cxy.blog.service.ConfigService;
import com.cxy.blog.common.constant.ConfigConst;
import com.cxy.blog.common.constant.StorageType;
import com.cxy.blog.common.exception.BlogException;
import org.apache.commons.lang3.StringUtils;

 
public final class OSSFactory {
    private static ConfigService configService;

    static {
       configService = SpringUtils.getBean(ConfigService.class);
    }

    public static BaseStorage build(){
                 StorageConfig config = configService.getConfigObject(ConfigConst.FILE_STORAGE, StorageConfig.class);
        if(config.getType() == StorageType.LOCAL.getValue()){
            if(StringUtils.isBlank(config.getLocalDirectory())){
                                 String localDirectory=System.getProperty("user.dir")+"/src/main/resources/static";
                config.setLocalDirectory(localDirectory);
                config.setLocalDomain(configService.findByName(ConfigConst.DOMAIN));
            }
            return new LocalStorage(config);
        }
        throw new BlogException("未配置存储类型");
    }
}
