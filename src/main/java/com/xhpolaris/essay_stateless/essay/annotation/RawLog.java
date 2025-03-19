package com.xhpolaris.essay_stateless.essay.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation: RawLog - 方法级 - 运行时
 * field:      uri - 日志来源的接口uri
 * effect: 被注解的方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RawLog {
    String value() default "";
}
