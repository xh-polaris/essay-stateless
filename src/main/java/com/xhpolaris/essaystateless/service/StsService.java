package com.xhpolaris.essaystateless.service;

import com.xhpolaris.essaystateless.entity.ocr.BeeOcrResponse;
import com.xhpolaris.essaystateless.utils.BeeOcrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class StsService {
    private final BeeOcrUtil beeOcrUtil;

    public BeeOcrResponse beeOcrBase64(List<String> images) throws Exception {
        List<String> result = beeOcrUtil.OcrAll(images, "base64");
        BeeOcrResponse response = new BeeOcrResponse();
        response.content = result.get(0) + "\n" + result.get(1);
        return response;
    }

    public BeeOcrResponse beeOcrUrl(List<String> images) throws Exception {
        List<String> result = beeOcrUtil.OcrAll(images, "url");
        BeeOcrResponse response = new BeeOcrResponse();
        response.content = result.get(0) + "\n" + result.get(1);
        return response;
    }
}
