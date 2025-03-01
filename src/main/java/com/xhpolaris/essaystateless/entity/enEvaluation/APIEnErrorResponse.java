package com.xhpolaris.essaystateless.entity.enEvaluation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIEnErrorResponse {
    @JsonProperty("code")
    private int statusCode;

    @JsonProperty("data")
    private ResponseData responseData;

    public APIEnErrorResponse(int statusCode, ResponseData responseData) {
        this.statusCode = statusCode;
        this.responseData = responseData;
    }

    @Setter
    @Getter
    public static class ResponseData {
        private List<GrammarEdit> edits;
        private String text;

        public ResponseData(List<GrammarEdit> edits, String text) {
            this.edits = edits;
            this.text = text;
        }

    }

    @Setter
    @Getter
    public static class GrammarEdit {
        @JsonProperty("Error_type")
        private String errorType;

        @JsonProperty("Original_content")
        private String originalContent;

        @JsonProperty("Replacement_content")
        private String replacementContent;

        @JsonProperty("end_id")
        private int endId;

        @JsonProperty("start_id")
        private int startId;

        public GrammarEdit(String errorType, String originalContent,
                           String replacementContent, int startId, int endId) {
            this.errorType = errorType;
            this.originalContent = originalContent;
            this.replacementContent = replacementContent;
            this.startId = startId;
            this.endId = endId;
        }

    }

}
