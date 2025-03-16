package com.xhpolaris.essay_stateless.essay.req;

import lombok.Data;

@Data
public class EvaluateRequest {
    public String title;
    public String content;
    public Integer grade;
}
