package com.xhpolaris.essay_stateless.ocr.controller;

import com.xhpolaris.essay_stateless.ocr.req.DefaultOcrRequest;
import com.xhpolaris.essay_stateless.ocr.core.OcrCore;
import com.xhpolaris.essay_stateless.ocr.req.TitleOcrRequest;
import com.xhpolaris.essay_stateless.re.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sts")
@AllArgsConstructor
public class OcrController {
    private final OcrCore OCRCore;

    /**
     * @param provider OCR的提供者, textin or bee
     * @param imgType  OCR识别类型, url or base64
     * @param req      OCR识别请求
     * @return OCR 识别结果
     */
    @PostMapping("/ocr/{provider}/{imgType}")
    public Response DefaultOcr(@PathVariable String provider, @PathVariable String imgType, @RequestBody DefaultOcrRequest req) throws Exception {
        return Response.Succeed(OCRCore.defaultOcr(provider, imgType, req));
    }

    @PostMapping("/ocr/title/{provider}/{imgType}")
    public Response titleOcr(@PathVariable String provider, @PathVariable String imgType, @RequestBody TitleOcrRequest req) throws Exception {
        return Response.Succeed(OCRCore.titleOcr(provider, imgType, req));
    }
}
