package com.cxy.blog.common.storage;

import com.cxy.blog.common.storage.group.LocalGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.NotBlank;

 
@Data
public class StorageConfig {

     
    @Range(min=1, max=4, message = "类型错误")
    private Integer type;

     
    @NotBlank(message="本地存储路径不能为空", groups = LocalGroup.class)
    private String localDirectory;
     
    private String localPrefix;
     
    @NotBlank(message="本地目录映射的域名不能为空", groups = LocalGroup.class)
    @URL(message = "本地目录映射的域名格式不正确", groups = LocalGroup.class)
    private String localDomain;
}
