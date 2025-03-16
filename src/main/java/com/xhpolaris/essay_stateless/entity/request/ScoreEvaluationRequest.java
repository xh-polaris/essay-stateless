package com.xhpolaris.essay_stateless.entity.request;

import lombok.Data;

@Data
public class ScoreEvaluationRequest {
    private String title;
    private String text;
    private Integer grade;
    private String ocr;
    private String lang;
}
