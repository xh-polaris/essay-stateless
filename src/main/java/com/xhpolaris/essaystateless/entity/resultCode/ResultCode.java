/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-03-02
 * @Description:
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.entity.resultCode;

public interface ResultCode {
    boolean success();
    int code();
    String message();

}
