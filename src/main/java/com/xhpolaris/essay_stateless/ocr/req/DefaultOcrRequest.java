package com.xhpolaris.essay_stateless.ocr.req;


import lombok.Data;

import java.util.List;

@Data
public class DefaultOcrRequest implements OcrRequest {
    /**
     * 默认Ocr请求
     */

    // 图片列表，url 或 base64
    private List<String> images;

    // 剩余类型，all 或 handwriting 或 print
    private String leftType;
}
