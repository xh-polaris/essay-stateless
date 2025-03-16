package com.xhpolaris.essay_stateless.entity.evaluation;

import com.xhpolaris.essay_stateless.entity.evaluation.fields.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AIEvaluation {

    private final ModelVersion modelVersion;
    public OverallEvaluation overallEvaluation;
    public FluencyEvaluation fluencyEvaluation;
    public WordSentenceEvaluation wordSentenceEvaluation;
    public ExpressionEvaluation expressionEvaluation;
    public SuggestionEvaluation suggestionEvaluation;
    public List<ParagraphEvaluation> paragraphEvaluations;

    public AIEvaluation(ModelVersion mv) {
        modelVersion = mv;
        overallEvaluation = new OverallEvaluation();
        fluencyEvaluation = new FluencyEvaluation();
        wordSentenceEvaluation = new WordSentenceEvaluation();
        expressionEvaluation = new ExpressionEvaluation();
        suggestionEvaluation = new SuggestionEvaluation();
        paragraphEvaluations = new ArrayList<>();
    }

}
