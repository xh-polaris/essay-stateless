package com.xhpolaris.essaystateless.entity.evaluation.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class APIBadWordResponse extends APICommonResponse{
    private BadWordRecognizeData data;

    @Setter
    @Getter
    public static class BadWordRecognizeData {
        @JsonProperty("sents")
        private List<List<String>> sentences;
        private BadWordRecognizeResult results;
    }

    @Setter
    @Getter
    public static class BadWordRecognizeResult {
        private List<BadWord> cgec;
        private String type;
    }

    @Setter
    @Getter
    public static class BadWord {
        private List<String> label;
        @JsonProperty("paragraph_id")
        private Integer paragraphId;
        @JsonProperty("revised_sent")
        private String revisedSent;
        @JsonProperty("revised_token_idx")
        private List<Integer> revisedTokenIdx;
        @JsonProperty("sent_id")
        private Integer sendId;
        @JsonProperty("token_idx")
        private List<Integer> tokenIdx;
    }
}
