/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-03-02
 * @Description: 自定义异常类型
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.exception;

import com.xhpolaris.essaystateless.entity.resultCode.ResultCode;
import lombok.Getter;

@Getter
public class CustomizeException extends RuntimeException {
    private ResultCode resultCode;

    public CustomizeException(ResultCode resultCode) {
        super("错误代码:" + resultCode.code() + ",错误信息:" + resultCode.message());
        this.resultCode = resultCode;
    }

}
