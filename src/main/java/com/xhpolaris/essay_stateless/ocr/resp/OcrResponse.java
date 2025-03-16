package com.xhpolaris.essay_stateless.ocr.resp;

public interface OcrResponse {
    // 获取识别后的标题
    String getTitle();

    // 获取识别后的文本，用\n分割段落
    String getContent();

}
