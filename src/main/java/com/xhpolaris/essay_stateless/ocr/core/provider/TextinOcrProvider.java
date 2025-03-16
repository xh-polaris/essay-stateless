package com.xhpolaris.essay_stateless.ocr.core.provider;

import com.xhpolaris.essay_stateless.ocr.req.DefaultOcrRequest;
import com.xhpolaris.essay_stateless.ocr.resp.DefaultOcrResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Textin 平台的OCR
 * TODO:  完善
 */
@Component
public class TextinOcrProvider implements OcrProvider {
    @Override
    public DefaultOcrResponse ocr(String imgType, DefaultOcrRequest req) {
        return null;
    }
}
