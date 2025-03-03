/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-03-02
 * @Description:
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.entity.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.xhpolaris.essaystateless.exception.ExceptionCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {

    Integer code = 0;
    String message;
    @JsonUnwrapped
    T data;

    public ResponseResult(ExceptionCode exceptionCode) {
        this.code = exceptionCode.code();
        this.message = exceptionCode.message();
    }

    public ResponseResult(ExceptionCode exceptionCode, T t) {
        this.code = exceptionCode.code();
        this.message = exceptionCode.message();
        this.data = t;
    }
}