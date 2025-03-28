package com.xhpolaris.essay_stateless.essay.core.beta;

import com.xhpolaris.essay_stateless.essay.config.BetaConfig;
import com.xhpolaris.essay_stateless.essay.core.beta.fields.ModelVersion;
import com.xhpolaris.essay_stateless.essay.core.beta.fields.*;
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

    public AIEvaluation(BetaConfig.ModelVersion mv) {
        modelVersion = new ModelVersion(mv.getName(), mv.getVersion());
        overallEvaluation = new OverallEvaluation();
        fluencyEvaluation = new FluencyEvaluation();
        wordSentenceEvaluation = new WordSentenceEvaluation();
        expressionEvaluation = new ExpressionEvaluation();
        suggestionEvaluation = new SuggestionEvaluation();
        paragraphEvaluations = new ArrayList<>();
    }

}
