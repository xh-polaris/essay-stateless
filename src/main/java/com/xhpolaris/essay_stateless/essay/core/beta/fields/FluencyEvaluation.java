package com.xhpolaris.essay_stateless.essay.core.beta.fields;

import lombok.Data;

/**
 * 流畅度评价
 */
@Data
public class FluencyEvaluation {
    // 流畅度得分
    public int fluencyScore;
    // 流畅度评价
    public String fluencyDescription;
}
