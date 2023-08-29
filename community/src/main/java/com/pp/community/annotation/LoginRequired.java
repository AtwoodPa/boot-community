package com.pp.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ss_419
 */
@Target(ElementType.METHOD)// 该注解标注在方法上
@Retention(RetentionPolicy.RUNTIME)// 运行周期
public @interface LoginRequired {

}
