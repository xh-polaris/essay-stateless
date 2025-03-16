package com.xhpolaris.essay_stateless.ocr.controller;

import com.xhpolaris.essay_stateless.ocr.entity.BeeOcrResponse;
import com.xhpolaris.essay_stateless.ocr.req.BeeOcrRequest;
import com.xhpolaris.essay_stateless.ocr.core.StsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sts")
@AllArgsConstructor
public class StsController {
    private final StsService stsService;

    @PostMapping("/ocr/bee/url")
    public BeeOcrResponse beeOcrUrl(@RequestBody BeeOcrRequest req) throws Exception {
        try {
            return stsService.beeOcrUrl(req.getImages());
        } catch (Exception e) {
            throw new Exception("Bee Ocr 调用失败");
        }
    }

    @PostMapping("/ocr/bee/base64")
    public BeeOcrResponse beeOcrBase64(@RequestBody BeeOcrRequest req) throws Exception {
        try {
            return stsService.beeOcrBase64(req.getImages());
        } catch (Exception e) {
            throw new Exception("Bee Ocr 调用失败");
        }

    }
}
