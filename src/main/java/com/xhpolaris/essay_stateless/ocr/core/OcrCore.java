package com.xhpolaris.essay_stateless.ocr.core;

import com.xhpolaris.essay_stateless.ocr.core.provider.OcrProvider;
import com.xhpolaris.essay_stateless.ocr.core.provider.OcrProviderFactory;
import com.xhpolaris.essay_stateless.ocr.req.DefaultOcrRequest;
import com.xhpolaris.essay_stateless.ocr.req.TitleOcrRequest;
import com.xhpolaris.essay_stateless.ocr.resp.DefaultOcrResponse;
import com.xhpolaris.essay_stateless.ocr.resp.TitleOcrResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * OcrCore
 * 处理Ocr核心业务逻辑
 */

@Service
@AllArgsConstructor
@Slf4j
public class OcrCore {
    private final OcrProviderFactory factory;

    /**
     * 默认的ocr识别，只获得识别的文本结果
     *
     * @param provider 需要的ocr渠道
     * @param imgType  提供的图片类型 url | base64
     * @param req      ocr识别请求
     * @return ocr识别结果
     */
    public DefaultOcrResponse defaultOcr(String provider, String imgType, DefaultOcrRequest req) throws Exception {

        // 根据provider从工厂类中获取具体的Ocr处理类
        OcrProvider _provider = factory.getOcrProvider(provider);

        // 识别ocr结果
        return _provider.ocr(imgType, req);
    }

    public TitleOcrResponse titleOcr(String provider, String imgType, TitleOcrRequest req) throws Exception {

        // 根据provider从工厂类中获取具体的Ocr处理类
        OcrProvider _provider = factory.getOcrProvider(provider);

        // 识别ocr结果
        return _provider.titleOcr(imgType, req);
    }
}
