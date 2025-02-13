/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-02-11
 * @Description: 作文范围定位serive
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.service;

import com.xhpolaris.essaystateless.entity.location.LocationEssayResponse;
import com.xhpolaris.essaystateless.entity.location.LocationSectionResponse;
import com.xhpolaris.essaystateless.entity.request.LocationRequest;
import com.xhpolaris.essaystateless.utils.HttpClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class LocationService {
    private final String LOCATION_ESSAY = "http://47.100.82.212:8070/location_essay";
    private final String LOCATION_SECTION = "http://47.100.82.212:8070/location_section";
    private final HttpClient httpClient;

    /**
     * 封装作文范围定位API
     * @param imageBase64 图片的base64编码
     * @return
     */
    public LocationEssayResponse essayLocationBase64(String imageBase64) {
        Map<String, Object> data = new HashMap<>();
        data.put("image_base64", imageBase64);
        return httpClient.postForEntity(LOCATION_ESSAY, LocationEssayResponse.class, data);
    }

    /**
     * 封装作文段落定位API
     * @param imageBase64 图片的base64编码
     * @return
     */
    public LocationSectionResponse sectionLocationBase64(String imageBase64) {
        Map<String, Object> data = new HashMap<>();
        data.put("image_base64", imageBase64);
        return httpClient.postForEntity(LOCATION_SECTION, LocationSectionResponse.class, data);
    }
}
