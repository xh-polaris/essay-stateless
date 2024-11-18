package com.xhpolaris.essaystateless.entity.evaluation.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIExpressionResponse extends APICommonResponse {
    private ExpressionData data;

    @Setter
    @Getter
    public static class ExpressionData {
        private ExpressionResult results;
        private Integer score;
        @JsonProperty("sents")
        private List<List<String >> sentences;
    }

    @Setter
    @Getter
    public static class ExpressionResult {
        @JsonProperty("sent_relations")
        private List<SentenceRelation> sentenceRelations;
        private String type;
    }

    @Setter
    @Getter
    public static class SentenceRelation {
        @JsonProperty("paragraph_id")
        private Integer paragraphId;
        private Integer start;
        private Integer end;
        private String label;
    }
}
