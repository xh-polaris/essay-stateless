package com.xhpolaris.essaystateless.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
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

    public List<String> OcrAll(List<String> items, String ImageType,String ReserveType) throws Exception {
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
                if (ImageType.equals("base64"))
                    body.put("image_base64", item);
                else if (ImageType.equals("url"))
                    body.put("image_url", item);
                HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

                log.info("调用Bee-Ocr");


                //ResponseEntity<String> response = restTemplate.postForEntity(beeOcrUrl, request, String.class);
                ResponseEntity<String> response = null;
                try {
                    response = restTemplate.postForEntity(beeOcrUrl, request, String.class);
                    // 这里可以添加成功后的处理逻辑
                } catch (HttpClientErrorException e) {
                    // 处理HttpClientErrorException，例如当HTTP状态码为4xx时
                    System.err.println("Client error occurred: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
                } catch (HttpServerErrorException e) {
                    // 处理HttpServerErrorException，例如当HTTP状态码为5xx时
                    System.err.println("Server error occurred: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
                } catch (ResourceAccessException e) {
                    // 处理网络访问异常，例如连接超时、资源不可用等
                    System.err.println("Resource access error occurred: " + e.getMessage());
                } catch (Exception e) {
                    // 处理其他可能的异常
                    System.err.println("An error occurred: " + e.getMessage());
                }

                log.info(response.getBody());
                // 处理识别逻辑
                if (response.getStatusCode() == HttpStatus.OK) {
                    // log.info("正常获取");
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

                    // log.info("处理保留信息");
                    // 找出所有非手写区域，记录在exclude中
                    // 将保留类型ReserveType转换为全小写的CharacterType,进而选择exclude中记录哪些待删去文字
                    String CharacterType = ReserveType.toLowerCase();
                    // handwritten
                    log.info(CharacterType);
                    switch(CharacterType){
                        case "handwritten":
                            for (Map<String, Object> line : lines) {
                                if ((int) line.get("handwritten") == 0) {
                                    exclude.add((Integer) line.get("area_index"));
                                }
                            }
                            break;
                        case "print":
                            for (Map<String, Object> line : lines) {
                                if ((int) line.get("print") != 1) {
                                    exclude.add((Integer) line.get("area_index"));
                                }
                            }
                        default:
                            break;
                    }

                    // 若参数为空，或为all，则保留所有文字
                    log.info("areas"+areas.toString());
                    // 将所有手写区域拼接到result中
                    for (Map<String, Object> area : areas) {
                        // log.info();
                        if (!exclude.contains((Integer) area.get("index"))) {
                            String text = (String) area.get("text");
                            if (!text.isEmpty()) {
                                result.add(text);
                            }
                        }
                    }
                }
                log.info("处理完成");
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

    // 定义两个参数的OcrAll方法
    public List<String> OcrAll(List<String> items, String imageType) throws Exception{
        // 调用三个参数的OcrAll方法，将ReserveType设置为空字符串
        return OcrAll(items, imageType, "");
    }
}
