package com.xhpolaris.essay_stateless.essay.core.beta.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

/**
 * APICommon
 * 下游算法接口通用的响应
 * 所有下游算法接口响应都需要继承这个类
 */
@Getter
@Setter
public class APICommon {
    // 响应码
    private Integer code;
    // 响应信息
    private String message;
}