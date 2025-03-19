package com.xhpolaris.essay_stateless.essay.core.beta.fields;

import lombok.Data;

/**
 * 总评
 */
@Data
public class OverallEvaluation {
    // 总评得分
    public int topicRelevanceScore;
    // 总评描述
    public String description;
}
