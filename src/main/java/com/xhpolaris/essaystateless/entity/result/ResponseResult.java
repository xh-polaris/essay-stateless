/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-03-02
 * @Description:
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.entity.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xhpolaris.essaystateless.entity.resultCode.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {

    Boolean success = true;
    Integer code = 200;
    String message;
    T data;

    public ResponseResult(ResultCode resultCode) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public ResponseResult(ResultCode resultCode,T t) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = t;
    }
}