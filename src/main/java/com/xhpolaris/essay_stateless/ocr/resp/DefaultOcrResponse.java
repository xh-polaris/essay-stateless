package com.xhpolaris.essay_stateless.ocr.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 默认Ocr响应,
 * 对于默认的ocr响应，由于现有的ocr没法很好的区分标题和正文的区别
 * 所以title暂且为空，content是所有内容的合并
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultOcrResponse implements OcrResponse {

    // 识别后的标题
    private String title;

    // 识别后内容，段落用/n分割
    private String content;
}
