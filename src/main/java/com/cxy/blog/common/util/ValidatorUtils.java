package com.cxy.blog.common.util;

import com.cxy.blog.common.constant.CodeEnum;
import com.cxy.blog.common.constant.StorageType;
import com.cxy.blog.common.exception.BlogException;
import com.cxy.blog.common.storage.StorageConfig;
import com.cxy.blog.common.storage.*;
import com.cxy.blog.common.storage.group.LocalGroup;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

 
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = SpringUtils.getBean(Validator.class);
    }


    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BlogException(CodeEnum.VALIDATION_ERROR.getValue(),message);
        }
    }

    public static void isNull(Object obj, String message) {
        if (obj==null) {
            throw new BlogException(CodeEnum.VALIDATION_ERROR.getValue(),message);
        }
    }

     
    public static void validate(Object object, Class<?>... groups) throws BlogException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for(ConstraintViolation<Object> constraint:  constraintViolations){
                msg.append(constraint.getMessage());
            }
            throw new BlogException(CodeEnum.VALIDATION_ERROR.getValue(),msg.toString());
        }
    }

     
    public static void validateStorageConfig(String value) throws BlogException {
        StorageConfig storageConfig= JsonUtils.fromJson(value,StorageConfig.class);
        if(storageConfig.getType() == StorageType.LOCAL.getValue()){
                         validate(storageConfig, LocalGroup.class);
        }
    }
}
