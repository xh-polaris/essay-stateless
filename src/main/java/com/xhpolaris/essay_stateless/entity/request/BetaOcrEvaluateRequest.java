package com.xhpolaris.essay_stateless.entity.request;

import lombok.Data;

import java.util.List;

@Data
public class BetaOcrEvaluateRequest {
    private List<String> images;
    private Integer grade;
}
