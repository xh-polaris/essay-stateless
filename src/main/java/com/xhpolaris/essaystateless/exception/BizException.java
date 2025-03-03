/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-03-02
 * @Description: 自定义异常类型
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.exception;

import lombok.Getter;

@Getter
public class BizException extends Exception {
    private final ExceptionCode exceptionCode;

    public BizException(ExceptionCode exceptionCode) {
        super("错误代码:" + exceptionCode.code() + ",错误信息:" + exceptionCode.message());
        this.exceptionCode = exceptionCode;
    }
}
