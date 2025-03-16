package com.xhpolaris.essay_stateless.essay.req;

import lombok.Data;

@Data
public class ScoreEvaluationRequest {
    private String title;
    private String text;
    private Integer grade;
    private String ocr;
    private String lang;
}
