package com.xhpolaris.essay_stateless.essay.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "beta")
public class BetaConfig {
    // Beta API 相关配置
    @NestedConfigurationProperty
    private BetaApi api;

    // 模型相关配置
    @NestedConfigurationProperty
    private ModelVersion modelVersion;

    @Getter
    @Setter
    public static class BetaApi {
        private String overall;
        private String fluency;
        private String wordSentence;
        private String expression;
        private String suggestion;
        private String paragraph;
        private String grammarInfo;
        private String essayInfo;
    }

    @Getter
    @Setter
    public static class ModelVersion {
        private String name;
        private String version;
    }
}
