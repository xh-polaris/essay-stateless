package com.xhpolaris.essaystateless.entity.evaluation.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class APIFluencyResponse extends APICommonResponse {
    private FluencyData data;

    @Getter
    @Setter
    public static class FluencyData {
        private FluencyResult results;
        @JsonProperty("sents")
        private List<List<String>> sentences;
    }

    @Getter
    @Setter
    public static class FluencyResult {
        private Integer score;
        @JsonProperty("sick_sents")
        private List<SickSentence> sickSentences;
        private String type;
    }

    @Getter
    @Setter
    public static class SickSentence {
        private String label;
        @JsonProperty("paragraph_id")
        private Integer paragraphId;
        @JsonProperty("sent_id")
        private Integer sentenceId;
    }
}
