/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-03-02
 * @Description:
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.exception;

public enum ExceptionCode {
    // 操作成功
    SUCCESS(0, "操作成功"),

    // 不可预知错误码
    UNPREDICTABLE_ERRORS(999, "发生了不可预知的错误"),

    // Location相关错误码
    // 上传参数错误
    LOCATION_UPLOAD_IMAGE_ERROR(1001, "location 上传的Base64参数错误"),
    // 访问服务器错误
    LOCATION_SERVER_ERROR(1002, "location 访问服务器失败"),
    // 图片处理错误
    LOCATION_IMAGE_DEAL_ERROR(1003, "Location 图片处理错误"),

    // BeeOcr相关错误码
    // ocr 配置出错
    BEE_OCR_CONFIG_ERROR(2001, "ocr 配置错误"),
    // ocr 识别错误
    BEE_OCR_IDENTIFY_ERROR(2002, "ocr 识别失败"),
    // ocr未识别到手写内容
    BEE_OCR_WRITTEN_NOT_IDENTIFY(2003, "ocr未识别到手写内容"),

    // Module相关错误码
    // 访问服务器错误
    MODULE_SERVER_ERROR(3001, "module 访问服务器失败"),

    // Evaluate相关错误码
    // 批改失败
    EVALUATE_GRADE_ERROR(4001, "批改失败，请重试");

    Integer code;
    String message;

    ExceptionCode(int code, String message) {
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
