package com.xhpolaris.essay_stateless.ocr.core.provider;

import com.xhpolaris.essay_stateless.ocr.req.DefaultOcrRequest;
import com.xhpolaris.essay_stateless.ocr.req.TitleOcrRequest;
import com.xhpolaris.essay_stateless.ocr.resp.DefaultOcrResponse;
import com.xhpolaris.essay_stateless.ocr.resp.TitleOcrResponse;
import org.springframework.stereotype.Component;

/**
 * Ocr接口
 * 不同渠道的Ocr均需实现这个
 */
@Component
public interface OcrProvider {
    /**
     * 默认ocr识别
     *
     * @param imgType OCR识别类型, url or base64
     * @param req     OCR请求信息
     * @return OCR识别结果, 无title，有content
     */
    DefaultOcrResponse ocr(String imgType, DefaultOcrRequest req) throws Exception;

    /**
     * 带标题OCR识别
     *
     * @param imgType OCR识别类型, url or base64
     * @param req     OCR请求信息
     * @return OCR识别结果, 有title，有content
     */
    TitleOcrResponse titleOcr(String imgType, TitleOcrRequest req) throws Exception;
}
