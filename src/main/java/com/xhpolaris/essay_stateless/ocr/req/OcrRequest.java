package com.xhpolaris.essay_stateless.ocr.req;

import java.util.List;

public interface OcrRequest {
    // 获取 图片列表，url 或 base64
    List<String> getImages();

    // 获取 剩余类型，all 或 handwriting 或 print
    String getLeftType();
}
