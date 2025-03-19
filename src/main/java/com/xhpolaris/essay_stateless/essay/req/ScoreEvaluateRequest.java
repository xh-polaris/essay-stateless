package com.xhpolaris.essay_stateless.essay.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScoreEvaluateRequest implements EvaluateRequest {
    private String title;
    private String text;
    private Integer grade;
    private String ocr;
    private String lang;

    @Override
    public String jsonString() {
        return String.format("{\"title\":\"%s\",\"text\":\"%s\",\"grade\":%d,\"ocr\":\"%s\",\"lang\":\"%s\"}", title, text, grade, ocr, lang);
    }
}
