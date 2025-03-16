package com.xhpolaris.essay_stateless.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@AllArgsConstructor
@Slf4j
public class HttpClient {

    private final Environment env;

    public <T> T postForEntity(String url, java.lang.Class<T> responseType, Map<String, Object> body) throws RestClientException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        log.info("向 {}发送请求", url);

        ResponseEntity<T> response;
        try {
            response = restTemplate.postForEntity(url, request, responseType);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("出错url {}", url);
            return null;
        }
    }

    public String getURL(String url) {
        return env.getProperty("api." + url);
    }

    public <T> CompletableFuture<T> asyncCall(String url, Class<T> responseClass, Map<String, Object> body) {
        return CompletableFuture.supplyAsync(() -> this.postForEntity(
                this.getURL(url), responseClass, body));
    }

    public <T> T syncCall(String url, Class<T> responseClass, Map<String, Object> body) {
        return this.postForEntity(this.getURL(url), responseClass, body);
    }
}
