package com.xhpolaris.essay_stateless.ocr.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标题OCR响应
 * 除主要内容外还会携带content
 */
@Getter
@AllArgsConstructor
public class TitleOcrResponse {
    // 识别后的标题
    private String title;

    // 识别后内容，段落用/n分割
    private String content;
}
