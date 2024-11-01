package com.xhpolaris.essaystateless.utils;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Component
@AllArgsConstructor
public class HttpClient {

    private final Environment env;

    public <T> T postForEntity(String url, java.lang.Class<T> responseType, Map<String, Object> body) throws RestClientException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        System.out.println("向 " + url + "发送请求");

        ResponseEntity<T> response;
        try {
            response = restTemplate.postForEntity(url, request, responseType);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getURL(String url) {
        return env.getProperty("api." + url);
    }
}
