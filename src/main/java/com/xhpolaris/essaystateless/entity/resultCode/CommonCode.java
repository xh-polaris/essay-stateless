/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-03-02
 * @Description:
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.entity.resultCode;

public enum CommonCode implements ResultCode {
    // 操作成功
    SUCCESS(true, 200, "操作成功"),

    // Location相关错误码
    // 上传参数错误
    LOCATION_UPLOAD_IMAGE_ERROR(false, 1001, "上传的Base64参数错误"),
    // 访问服务器错误
    LOCATION_SERVER_ERROR(false, 1002, "访问服务器失败"),
    // 图片处理错误
    LOCATION_IMAGE_DEAL_ERROR(false, 1003, "Location 图片处理错误"),

    // BeeOcr相关错误码
    // ocr 配置出错
    BEE_OCR_CONFIG_ERROR(false, 2001, "Ocr 配置错误"),
    // ocr 识别错误
    BEE_OCR_IDENTIFY_ERROR(false, 2002, "ocr 识别失败"),
    // ocr未识别到手写内容
    BEE_OCR_WRITTEN_NOT_IDENTIFY(false, 2003, "ocr未识别到手写内容");



    Boolean success;
    Integer code;
    String message;

    CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
    }
