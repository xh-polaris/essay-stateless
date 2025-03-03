package com.xhpolaris.essaystateless.controller;

import com.xhpolaris.essaystateless.entity.location.LocationEssayResponse;
import com.xhpolaris.essaystateless.entity.location.LocationSectionResponse;
import com.xhpolaris.essaystateless.entity.ocr.BeeOcrResponse;
import com.xhpolaris.essaystateless.entity.request.BeeOcrRequest;
import com.xhpolaris.essaystateless.entity.request.LocationRequest;
import com.xhpolaris.essaystateless.service.LocationService;
import com.xhpolaris.essaystateless.service.StsService;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sts")
@AllArgsConstructor
public class StsController {
    private final StsService stsService;
    private final LocationService locationService;

    @PostMapping("/ocr/bee/url")
    public BeeOcrResponse beeOcrUrl(@RequestBody BeeOcrRequest req) throws Exception {
        try {
            // 增加参数type
            return stsService.beeOcrUrl(req.getImages(),req.getType());
        } catch (Exception e) {
            throw new Exception("Bee Ocr 调用失败");
        }
    }

    @PostMapping("/ocr/bee/base64")
    public BeeOcrResponse beeOcrBase64(@RequestBody BeeOcrRequest req) throws Exception {
        try {
            return stsService.beeOcrBase64(req.getImages(),req.getType());
        } catch (Exception e) {
            throw new Exception("Bee Ocr 调用失败");
        }
    }

    @PostMapping("/location/crop/essay/base64")
    public String essayLocationBase64(@RequestBody LocationRequest req) throws Exception {
        try {
            return locationService.essayCropLocationBase64(req.getImageBase64());
        } catch (Exception e) {
            throw new Exception("作文范围定位 调用失败");
        }
    }

    @PostMapping("/location/crop/section/base64")
    public String[] sectionLocationBase64(@RequestBody LocationRequest req) throws Exception {
        try {
            return locationService.sectionCropLocationBase64(req.getImageBase64());
        } catch (Exception e) {
            throw new Exception("作文段落定位 调用失败");
        }
    }

    @PostMapping("/ocr/advanced/section/bee/base64")
    public BeeOcrResponse beeOcrAdvancedSectionBase64(@RequestBody BeeOcrRequest req) throws Exception {
        try {
            List<String> images = req.getImages();
            List<String> CroppedImages = new ArrayList<String>();
            for(String image : images){
                String[] SectionImages = locationService.sectionCropLocationBase64(image);
                CroppedImages.addAll(Arrays.asList(SectionImages));
            }
            return stsService.beeOcrBase64(CroppedImages,req.getType());
        } catch (Exception e) {
            throw new Exception("Bee Ocr 调用失败");
        }
    }

    @PostMapping("/ocr/advanced/essay/bee/base64")
    public BeeOcrResponse beeOcrAdvancedEssayBase64(@RequestBody BeeOcrRequest req) throws Exception {
        try {
            List<String> images = req.getImages();
            List<String> CroppedImages = new ArrayList<String>();
            for(String image : images){
                String EssayImages = locationService.essayCropLocationBase64(image);
                if(!EssayImages.isEmpty())
                    CroppedImages.add(EssayImages);
            }
            return stsService.beeOcrBase64(CroppedImages,req.getType());
        } catch (Exception e) {
            throw new Exception("Bee Ocr 调用失败");
        }
    }
}
