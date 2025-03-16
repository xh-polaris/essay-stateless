package com.xhpolaris.essay_stateless.exception;

import com.xhpolaris.essay_stateless.re.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 全局异常处理
     * BizException & code >= 1000 - HttpStatus:200, 用户友好提示
     * BizException & code <  1000 - HttpStatus:500, log
     * Other Exception - HttpStatus:500, log
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handle(Exception e) {
        // 用户友好的业务异常
        if (e instanceof BizException be && be.getCode() > 1000) {
            log.info("biz error: {}", e.getMessage());
            return ResponseEntity.ok(Response.Failed(be));
        }
        // 非用户友好或其余异常
        else {
            log.error("exception: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.Failed(e));
        }
    }
}