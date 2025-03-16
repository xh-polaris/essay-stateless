package com.xhpolaris.essay_stateless.exception;


public enum ECode {
    /*
     * 错误码
     * 系统异常 - 1~999 - 需要修改HttpStatus为500, 不需要用户友好提示
     * 业务异常 - 1000~ - 需要保留HttpStatus为200, msg中给出用户友好提示
     * 预留的值 - 0     - 默认成功
     * 预留的值 - 999   - 未知系统异常
     * 预留的值 - 9999  - 未知的业务异常，可作为临时添加的业务异常，需要给出用户友好提示
     *
     */

    // 预留的值
    SUCCESS(0, "success"),
    SYS_UNKNOWN(999, "未知的系统异常"),
    BIZ_UNKNOWN(9999, "未知的业务异常"),

    // 系统异常 1~999

    // 业务异常 1000~

    // 批改相关 1000+
    BIZ_ESSAY_DEFAULT(1000,"批改失败"),

    // OCR相关 2000+
    BIZ_OCR_DEFAULT(2000,"OCR识别失败");


    final Integer code;
    final String message;

    ECode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int code() {
        return code;
    }


    public String message() {
        return message;
    }
}