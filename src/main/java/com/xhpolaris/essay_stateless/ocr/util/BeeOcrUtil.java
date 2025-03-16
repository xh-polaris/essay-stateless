package com.xhpolaris.essay_stateless.ocr.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class BeeOcrUtil {
    /*
      蜜蜂家教ocr接口工具类
     */

    private final Environment env;
    private final ObjectMapper objectMapper;

    public List<String> OcrAll(List<String> items, String type) throws Exception {
        String beeOcrUrl = env.getProperty("bee.ocr");
        String secret = env.getProperty("bee.x-app-secret");
        String key = env.getProperty("bee.x-app-key");

        if (beeOcrUrl == null) {
            throw new Exception("ocr 配置错误");
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-app-secret", secret);
        headers.add("x-app-key", key);

        List<String> result = new ArrayList<>();
        try {
            for (String item : items) {
                Map<String, String> body = new HashMap<>();
                if (type.equals("base64"))
                    body.put("image_base64", item);
                else if (type.equals("url"))
                    body.put("image_url", item);
                HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

                log.info("调用Bee-Ocr");


                ResponseEntity<String> response = restTemplate.postForEntity(beeOcrUrl, request, String.class);
                // 处理识别逻辑
                if (response.getStatusCode() == HttpStatus.OK) {
                    Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<>() {
                    });
                    if ((int) responseMap.get("code") != 0) {
                        throw new Exception("ocr 识别失败");
                    }
                    Map<String, Object> data = objectMapper.convertValue(responseMap.get("data"), new TypeReference<Map<String, Object>>() {
                    });  // 获取响应中的数据部分
                    List<Map<String, Object>> lines = objectMapper.convertValue(data.get("lines"), new TypeReference<List<Map<String, Object>>>() {
                    });
                    List<Map<String, Object>> areas = objectMapper.convertValue(data.get("areas"), new TypeReference<List<Map<String, Object>>>() {
                    });
                    List<Integer> exclude = new ArrayList<>();

                    // 找出所有非手写区域，记录在exclude中
                    for (Map<String, Object> line : lines) {
                        if ((int) line.get("handwritten") == 0) {
                            exclude.add((Integer) line.get("area_index"));
                        }
                    }

                    // 将所有手写区域拼接到result中
                    for (Map<String, Object> area : areas) {
                        if (!exclude.contains((Integer) area.get("index"))) {
                            String text = (String) area.get("text");
                            if (!text.isEmpty()) {
                                result.add(text);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("ocr 识别失败");
        }

        if (result.isEmpty()) {
            throw new Exception("ocr未识别到手写内容");
        }

        String title = result.get(0);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < result.size(); i++) {
            sb.append(result.get(i));
            sb.append("\n");
        }
        String text = sb.toString();

        return List.of(title, text);
    }
}
