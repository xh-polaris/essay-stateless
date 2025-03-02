/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-02-11
 * @Description: 作文范围定位serive
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.service;

import com.xhpolaris.essaystateless.entity.location.LocationEssayCropResponse;
import com.xhpolaris.essaystateless.entity.location.LocationEssayResponse;
import com.xhpolaris.essaystateless.entity.location.LocationSectionCropResponse;
import com.xhpolaris.essaystateless.entity.location.LocationSectionResponse;
import com.xhpolaris.essaystateless.entity.result.ResponseResult;
import com.xhpolaris.essaystateless.entity.resultCode.CommonCode;
import com.xhpolaris.essaystateless.exception.CustomizeException;
import com.xhpolaris.essaystateless.utils.HttpClient;
import com.xhpolaris.essaystateless.utils.ImageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class LocationService {
    private final HttpClient httpClient;
    private final ImageUtil imageUtil;

    public ResponseResult<LocationEssayCropResponse> essayCropLocationBase64(String imageBase64) {
        try {
            // 调用API
            LocationEssayResponse locationEssayResponse = postForLocationEssay(imageBase64);
            if (locationEssayResponse == null) {
                throw new CustomizeException(CommonCode.LOCATION_UPLOAD_IMAGE_ERROR);
            } else if (locationEssayResponse.getEssayBox() == null) {
                throw new CustomizeException(CommonCode.LOCATION_SERVER_ERROR);
            }
            // 解码
            BufferedImage image = imageUtil.base64ToImage(imageBase64);
            // 裁剪
            // 计算宽度、高度
            double[] boxLocation = locationEssayResponse.getEssayBox().getLocation()[0];
            int x1 = (int) (boxLocation[0]), y1 = (int) (boxLocation[1]);
            int x2 = (int) (boxLocation[2]), y2 = (int) (boxLocation[3]);
            image = imageUtil.cropImage(image, x1, y1, x2, y2);

            return new ResponseResult<>(CommonCode.SUCCESS, new LocationEssayCropResponse(imageUtil.imageToBase64(image)));
        } catch (IOException e) {
            throw new CustomizeException(CommonCode.LOCATION_IMAGE_DEAL_ERROR);
        }
    }

    public ResponseResult<LocationSectionCropResponse> sectionCropLocationBase64(String imageBase64) {
        try {
            // 调用API
            LocationSectionResponse locationSectionResponse = postForLocationSection(imageBase64);
            if (locationSectionResponse == null) {
                throw new CustomizeException(CommonCode.LOCATION_UPLOAD_IMAGE_ERROR);
            } else if (locationSectionResponse.getSectionBox() == null) {
                throw new CustomizeException(CommonCode.LOCATION_SERVER_ERROR);
            }
            // 解码
            BufferedImage image = imageUtil.base64ToImage(imageBase64);
            // 裁剪
            double[][] sectionBox = locationSectionResponse.getSectionBox();
            String[] result = new String[sectionBox.length];
            int index = 0;
            for (double[] box : sectionBox) {
                int x1 = (int) (box[0]), y1 = (int) (box[1]);
                int x2 = (int) (box[2]), y2 = (int) (box[3]);
                image = imageUtil.cropImage(image, x1, y1, x2, y2);
                result[index++] = imageUtil.imageToBase64(image);
            }
            return new ResponseResult<>(CommonCode.SUCCESS, new LocationSectionCropResponse(result));
        } catch (IOException e) {
            throw new CustomizeException(CommonCode.LOCATION_IMAGE_DEAL_ERROR);
        }
    }

    /**
     * 封装作文范围定位API
     *
     * @param imageBase64 图片的base64编码
     * @return
     */
    public ResponseResult<LocationEssayResponse> essayLocationBase64(String imageBase64) {
        LocationEssayResponse response = postForLocationEssay(imageBase64);
        if (response == null) {
            throw new CustomizeException(CommonCode.LOCATION_UPLOAD_IMAGE_ERROR);
        } else if (response.getEssayBox() == null) {
            throw new CustomizeException(CommonCode.LOCATION_SERVER_ERROR);
        }
        return new ResponseResult<>(CommonCode.SUCCESS, response);
    }

    /**
     * 封装作文段落定位API
     *
     * @param imageBase64 图片的base64编码
     * @return
     */
    public ResponseResult<LocationSectionResponse> sectionLocationBase64(String imageBase64) {
        LocationSectionResponse response = postForLocationSection(imageBase64);
        if (response == null) {
            throw new CustomizeException(CommonCode.LOCATION_UPLOAD_IMAGE_ERROR);
        } else if (response.getSectionBox() == null) {
            throw new CustomizeException(CommonCode.LOCATION_SERVER_ERROR);
        }
        return new ResponseResult<>(CommonCode.SUCCESS, response);
    }

    private LocationEssayResponse postForLocationEssay(String imageBase64) {
        imageBase64 = imageBase64.replace("data:image/jpeg;base64,", "");
        Map<String, Object> data = new HashMap<>();
        data.put("image_base64", imageBase64);
        String LOCATION_ESSAY = "http://47.100.82.212:8070/location_essay";
        return httpClient.postForEntity(LOCATION_ESSAY, LocationEssayResponse.class, data);
    }

    private LocationSectionResponse postForLocationSection(String imageBase64) {
        imageBase64 = imageBase64.replace("data:image/jpeg;base64,", "");
        Map<String, Object> data = new HashMap<>();
        data.put("image_base64", imageBase64);
        String LOCATION_SECTION = "http://47.100.82.212:8070/location_section";
        return httpClient.postForEntity(LOCATION_SECTION, LocationSectionResponse.class, data);
    }
}
