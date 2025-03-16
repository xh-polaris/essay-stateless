package com.xhpolaris.essay_stateless.exception;

import lombok.Getter;

@Getter
public class BizException extends Exception {
    private final int code;
    private final String msg;

    public BizException(ECode eCode) {
        super("错误代码:" + eCode.code() + ",错误信息:" + eCode.message());
        this.code = eCode.code();
        this.msg = eCode.message();
    }

    public BizException(ECode eCode, String msg) {
        super("错误代码:" + eCode.code() + ",错误信息:" + msg);
        this.code = eCode.code();
        this.msg = msg;
    }
}