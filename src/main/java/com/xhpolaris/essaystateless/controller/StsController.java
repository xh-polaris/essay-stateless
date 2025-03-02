package com.xhpolaris.essaystateless.controller;

import com.xhpolaris.essaystateless.entity.location.LocationEssayCropResponse;
import com.xhpolaris.essaystateless.entity.location.LocationEssayResponse;
import com.xhpolaris.essaystateless.entity.location.LocationSectionCropResponse;
import com.xhpolaris.essaystateless.entity.location.LocationSectionResponse;
import com.xhpolaris.essaystateless.entity.ocr.BeeOcrResponse;
import com.xhpolaris.essaystateless.entity.request.BeeOcrRequest;
import com.xhpolaris.essaystateless.entity.request.LocationRequest;
import com.xhpolaris.essaystateless.service.LocationService;
import com.xhpolaris.essaystateless.service.StsService;
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
    private final LocationService locationService;

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

    @PostMapping("/location/crop/essay/base64")
    public LocationEssayCropResponse essayCropLocationBase64(@RequestBody LocationRequest req) throws Exception {
        try {
            return locationService.essayCropLocationBase64(req.getImageBase64());
        } catch (Exception e) {
            // TODO:异常处理
            // 错误码、错误信息 code = 1000以上
            // 返回错误信息响应值200
            throw new Exception("作文范围定位 调用失败");

        }
    }

    @PostMapping("/location/crop/section/base64")
    public LocationSectionCropResponse sectionCropLocationBase64(@RequestBody LocationRequest req) throws Exception {
        try {
            return locationService.sectionCropLocationBase64(req.getImageBase64());
        } catch (Exception e) {
            throw new Exception("作文段落定位 调用失败");
        }
    }

    @PostMapping("/location/position/essay/base64")
    public LocationEssayResponse essayLocationBase64(@RequestBody LocationRequest req) throws Exception {
        try {
            return locationService.essayLocationBase64(req.getImageBase64());
        } catch (Exception e) {
            throw new Exception("作文范围定位 调用失败");
        }
    }

    @PostMapping("/location/position/section/base64")
    public LocationSectionResponse sectionLocationBase64(@RequestBody LocationRequest req) throws Exception {
        try {
            return locationService.sectionLocationBase64(req.getImageBase64());
        } catch (Exception e) {
            throw new Exception("作文段落定位 调用失败");
        }
    }
}
