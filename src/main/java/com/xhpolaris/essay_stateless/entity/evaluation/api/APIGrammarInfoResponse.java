package com.xhpolaris.essay_stateless.entity.evaluation.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIGrammarInfoResponse extends APICommonResponse{
    private GrammarInfo grammar;

    @Setter
    @Getter
    public static class GrammarInfo {
        private List<Typo> typo;
    }

    @Setter
    @Getter
    public static class Typo {
        @JsonProperty("end_pos")
        private Integer endPos;
        @JsonProperty("start_pos")
        private Integer startPos;
        private String extra;
        private String ori;
        private String revised;
        private String type;
    }
}
