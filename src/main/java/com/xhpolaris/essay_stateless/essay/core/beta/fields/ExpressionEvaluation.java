package com.xhpolaris.essay_stateless.essay.core.beta.fields;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 流畅度表达
 */
@Data
public class ExpressionEvaluation {
    // 表达得分
    public int expressionScore;
    // 表达评价
    public String expressDescription;
}
