package com.sky.annotation;

/* 
    @author nanchao 
    @date 2025/5/14
*/


import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//自定义注解，用于标识某个方法需要进行功能字段自动填充处理(insert和update方法中 set time和set user)

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //数据库操作类型：update和insert
    OperationType value();
}
