package com.xhpolaris.essaystateless.entity.evaluation.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class APIWordSentenceResponse extends APICommonResponse {
    private WordSentenceData data;

    @Getter
    @Setter
    public static class WordSentenceData {
        @JsonProperty("sents")
        private List<List<String>> sentences;
        private WordSentenceResult results;
    }

    @Getter
    @Setter
    public static class WordSentenceResult {
        private String type;
        @JsonProperty("good_words")
        List<GoodWordResult> goodWords;
        @JsonProperty("good_sents")
        List<GoodSentenceResult> goodSents;
    }

    @Getter
    @Setter
    public static class GoodWordResult {
        @JsonProperty("paragraph_id")
        private Integer paragraphId;
        @JsonProperty("sent_id")
        private Integer sentId;
        private Integer start;
        private Integer end;
    }

    @Getter
    @Setter
    public static class GoodSentenceResult {
        @JsonProperty("paragraph_id")
        private Integer paragraphId;
        @JsonProperty("sent_id")
        private Integer sentId;
        private Integer label;
    }
}
