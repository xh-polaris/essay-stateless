package com.xhpolaris.essay_stateless.essay.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "model-version")
public class ModelVersion {

    // 模型名称
    public String name;
    // 模型版本
    public String version;

}
