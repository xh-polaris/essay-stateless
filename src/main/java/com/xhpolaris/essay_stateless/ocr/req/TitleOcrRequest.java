package com.xhpolaris.essay_stateless.ocr.req;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 标题OCR请求
 * 除了主体外，还会自动划分标题
 */
@Getter
@AllArgsConstructor
public class TitleOcrRequest {
    // 图片列表，url 或 base64
    private List<String> images;

    // 剩余类型，all 或 handwriting 或 print
    private String leftType;
}
