/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-03-02
 * @Description: 异常捕获/抛出类
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.exception;

import com.xhpolaris.essaystateless.entity.resultCode.ResultCode;

public class ExceptionCast {
    public static void cast(ResultCode resultCode) {
        throw new CustomizeException(resultCode);
    }
}
