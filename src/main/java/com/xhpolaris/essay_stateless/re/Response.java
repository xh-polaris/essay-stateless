package com.xhpolaris.essay_stateless.re;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.xhpolaris.essay_stateless.exception.BizException;
import com.xhpolaris.essay_stateless.exception.ECode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    /**
     * 统一响应
     */

    int code;
    String msg;

    // 由于历史原因，data需要平铺而不是包裹在data中
    @JsonUnwrapped
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Object data;

    // 默认成功
    public static Response Succeed(Object data) {
        return new Response(0, "success", data);
    }

    // 业务异常 失败
    public static Response Failed(BizException e) {
        return new Response(e.getCode(), e.getMessage(), null);
    }

    // 其他异常 失败
    public static Response Failed(Exception e) {
        return Failed(new BizException(ECode.SYS_UNKNOWN, e.getMessage()));
    }
}
