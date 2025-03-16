package com.xhpolaris.essay_stateless.essay.entity.evaluation.fields;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExpressionEvaluation {
    public int expressionScore;
    public String expressDescription;
    public List<List<SubExpressionEvaluation>> expressionEvaluations = new ArrayList<>();

    @AllArgsConstructor
    public static class SubExpressionEvaluation {
        public int start;
        public int end;
        public String label;
    }
}
