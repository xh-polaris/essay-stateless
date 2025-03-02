package com.xhpolaris.essaystateless.controller;

import com.xhpolaris.essaystateless.entity.location.LocationEssayCropResponse;
import com.xhpolaris.essaystateless.entity.location.LocationEssayResponse;
import com.xhpolaris.essaystateless.entity.location.LocationSectionCropResponse;
import com.xhpolaris.essaystateless.entity.location.LocationSectionResponse;
import com.xhpolaris.essaystateless.entity.ocr.BeeOcrResponse;
import com.xhpolaris.essaystateless.entity.request.BeeOcrRequest;
import com.xhpolaris.essaystateless.entity.request.LocationRequest;
import com.xhpolaris.essaystateless.entity.result.ResponseResult;
import com.xhpolaris.essaystateless.entity.resultCode.CommonCode;
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
    public ResponseResult<BeeOcrResponse> beeOcrUrl(@RequestBody BeeOcrRequest req) throws Exception {
        try {
            return new ResponseResult<>(CommonCode.SUCCESS, stsService.beeOcrUrl(req.getImages()));
        } catch (Exception e) {
            throw new Exception("Bee Ocr 调用失败");
        }
    }

    @PostMapping("/ocr/bee/base64")
    public ResponseResult<BeeOcrResponse> beeOcrBase64(@RequestBody BeeOcrRequest req) throws Exception {
        try {
            return new ResponseResult<>(CommonCode.SUCCESS, stsService.beeOcrBase64(req.getImages()));
        } catch (Exception e) {
            throw new Exception("Bee Ocr 调用失败");
        }
    }

    @PostMapping("/location/crop/essay/base64")
    public ResponseResult<LocationEssayCropResponse> essayCropLocationBase64(@RequestBody LocationRequest req) {
        return locationService.essayCropLocationBase64(req.getImageBase64());
    }

    @PostMapping("/location/crop/section/base64")
    public ResponseResult<LocationSectionCropResponse> sectionCropLocationBase64(@RequestBody LocationRequest req) {
        return locationService.sectionCropLocationBase64(req.getImageBase64());
    }

    @PostMapping("/location/position/essay/base64")
    public ResponseResult<LocationEssayResponse> essayLocationBase64(@RequestBody LocationRequest req) throws Exception {
        return locationService.essayLocationBase64(req.getImageBase64());
    }

    @PostMapping("/location/position/section/base64")
    public ResponseResult<LocationSectionResponse> sectionLocationBase64(@RequestBody LocationRequest req) {
        return locationService.sectionLocationBase64(req.getImageBase64());
    }
}
