package com.xhpolaris.essay_stateless.essay.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "beta-api")
public class BetaUrlConfig {
    private String overall;
    private String fluency;
    private String wordSentence;
    private String expression;
    private String suggestion;
    private String paragraph;
    private String grammarInfo;
    private String essayInfo;
}
