package com.xhpolaris.essay_stateless.essay.resp;

/**
 * 所有批改响应类都需要实现的接口
 * 目前主要用于获取Json格式字符串以记录批改日志
 */
public interface EvaluateResponse {
    String jsonString();
}
