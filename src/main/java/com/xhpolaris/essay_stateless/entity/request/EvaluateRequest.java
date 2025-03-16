package com.xhpolaris.essay_stateless.entity.request;

import lombok.Data;

@Data
public class EvaluateRequest {
    public String title;
    public String content;
    public Integer grade;
}
