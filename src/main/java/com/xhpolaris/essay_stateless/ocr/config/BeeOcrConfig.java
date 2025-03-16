package com.xhpolaris.essay_stateless.ocr.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bee")
public class BeeOcrConfig {

    // 路由
    private String ocr;
    // key
    private String xAppKey;
    // 密钥
    private String xAppSecret;
}
