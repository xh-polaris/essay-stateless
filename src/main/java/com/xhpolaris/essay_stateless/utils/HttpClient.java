package com.xhpolaris.essay_stateless.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@AllArgsConstructor
@Slf4j
public class HttpClient {

    public <T> T postForEntity(String url, java.lang.Class<T> responseType, Map<String, Object> body, Map<String, String> header) throws RestClientException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        if (header != null) {
            header.forEach(headers::add);
        }

        ResponseEntity<T> response;
        try {
            response = restTemplate.postForEntity(url, request, responseType);
            return response.getBody();
        } catch (Exception e) {
            log.info("出错url {}; 出错原因 {}", url, e.getMessage());
            return null;
        }
    }

    // 异步调用接口
    public <T> CompletableFuture<T> asyncCall(String url, Class<T> responseClass, Map<String, Object> body, Map<String, String> header) {
        return CompletableFuture.supplyAsync(() -> this.postForEntity(url, responseClass, body, header));
    }

    // 同步调用接口
    public <T> T syncCall(String url, Class<T> responseClass, Map<String, Object> body, Map<String, String> header) {
        return this.postForEntity(url, responseClass, body, header);
    }
}
