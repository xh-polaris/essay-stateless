/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-03-02
 * @Description: 异常捕获类
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.exception;


import com.xhpolaris.essaystateless.entity.result.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 捕获自定义异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult customizeException(Exception e) {
        LOGGER.error("catch exception:{}\r\nexception", e.getMessage(), e);
        if (e instanceof BizException) {
            // 可预知错误
            ExceptionCode exceptionCode = ((BizException) e).getExceptionCode();
            return new ResponseResult<>(exceptionCode);
        } else {
            // 不可预知错误返回
            return new ResponseResult<>(ExceptionCode.UNPREDICTABLE_ERRORS);
        }
    }
}