package com.xhpolaris.essaystateless.controller;

import com.xhpolaris.essaystateless.entity.location.LocationEssayCropResponse;
import com.xhpolaris.essaystateless.entity.location.LocationEssayResponse;
import com.xhpolaris.essaystateless.entity.location.LocationSectionCropResponse;
import com.xhpolaris.essaystateless.entity.location.LocationSectionResponse;
import com.xhpolaris.essaystateless.entity.ocr.BeeOcrResponse;
import com.xhpolaris.essaystateless.entity.request.BeeOcrRequest;
import com.xhpolaris.essaystateless.entity.request.LocationRequest;
import com.xhpolaris.essaystateless.entity.result.ResponseResult;
import com.xhpolaris.essaystateless.exception.BizException;
import com.xhpolaris.essaystateless.exception.ExceptionCode;
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
        BeeOcrResponse response = stsService.beeOcrUrl(req.getImages());
        if (response == null) {
            throw new BizException(ExceptionCode.BEE_OCR_IDENTIFY_ERROR);
        }
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }

    @PostMapping("/ocr/bee/base64")
    public ResponseResult<BeeOcrResponse> beeOcrBase64(@RequestBody BeeOcrRequest req) throws Exception {
        BeeOcrResponse response = stsService.beeOcrBase64(req.getImages());
        if (response == null) {
            throw new BizException(ExceptionCode.LOCATION_SERVER_ERROR);
        }
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }

    @PostMapping("/location/crop/essay/base64")
    public ResponseResult<LocationEssayCropResponse> essayCropLocationBase64(@RequestBody LocationRequest req) throws Exception {
        LocationEssayCropResponse response = locationService.essayCropLocationBase64(req.getImageBase64());
        if (response == null) {
            throw new BizException(ExceptionCode.LOCATION_SERVER_ERROR);
        }
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }

    @PostMapping("/location/crop/section/base64")
    public ResponseResult<LocationSectionCropResponse> sectionCropLocationBase64(@RequestBody LocationRequest req) throws Exception {
        LocationSectionCropResponse response = locationService.sectionCropLocationBase64(req.getImageBase64());
        if (response == null) {
            throw new BizException(ExceptionCode.LOCATION_SERVER_ERROR);
        }
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }

    @PostMapping("/location/position/essay/base64")
    public ResponseResult<LocationEssayResponse> essayLocationBase64(@RequestBody LocationRequest req) throws Exception {
        LocationEssayResponse response = locationService.essayLocationBase64(req.getImageBase64());
        if (response == null) {
            throw new BizException(ExceptionCode.LOCATION_SERVER_ERROR);
        }
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }

    @PostMapping("/location/position/section/base64")
    public ResponseResult<LocationSectionResponse> sectionLocationBase64(@RequestBody LocationRequest req) throws Exception {
        LocationSectionResponse response = locationService.sectionLocationBase64(req.getImageBase64());
        if (response == null) {
            throw new BizException(ExceptionCode.LOCATION_SERVER_ERROR);
        }
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }
}
