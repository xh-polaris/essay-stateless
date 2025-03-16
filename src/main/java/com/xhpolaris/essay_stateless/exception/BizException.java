package com.xhpolaris.essay_stateless.exception;

import lombok.Getter;

/**
 * 自定义业务异常
 */
@Getter
public class BizException extends Exception {

    private final int code;
    private final String msg;

    // 由ECode构造自定义异常
    public BizException(ECode eCode) {
        super("错误代码:" + eCode.code() + ",错误信息:" + eCode.message());
        this.code = eCode.code();
        this.msg = eCode.message();
    }

    // 由ECode构造自定义异常，覆盖消息
    public BizException(ECode eCode, String msg) {
        super("错误代码:" + eCode.code() + ",错误信息:" + msg);
        this.code = eCode.code();
        this.msg = msg;
    }

    // 未知系统异常
    public static BizException FailedSysUn() {
        return new BizException(ECode.SYS_UNKNOWN);
    }

    // 未知系统异常 - 有消息
    public static BizException FailedSysUn(String msg) {
        return new BizException(ECode.SYS_UNKNOWN, msg);
    }

    // 未知业务异常
    public static BizException FailedBizUn() {
        return new BizException(ECode.BIZ_UNKNOWN);
    }

    // 未知业务异常 - 有消息
    public static BizException FailedBizUn(String msg) {
        return new BizException(ECode.BIZ_UNKNOWN, msg);
    }

    // 业务异常 失败
    public static BizException Failed(ECode eCode) {
        return new BizException(eCode);
    }

    // 其他异常 失败
    public static BizException Failed(Exception e) {
        return FailedSysUn(e.getMessage());
    }
}