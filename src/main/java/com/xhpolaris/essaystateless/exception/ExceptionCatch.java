/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-03-02
 * @Description: 异常捕获类
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.exception;


import com.xhpolaris.essaystateless.entity.result.ResponseResult;
import com.xhpolaris.essaystateless.entity.resultCode.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionCatch {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    /**
     * 捕获自定义异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(CustomizeException.class)
    public ResponseResult customizeException(CustomizeException e) {
        LOGGER.error("catch exception:{}\r\nexception", e.getMessage(), e);
        ResultCode resultCode = e.getResultCode();
        return new ResponseResult(resultCode);
    }
}