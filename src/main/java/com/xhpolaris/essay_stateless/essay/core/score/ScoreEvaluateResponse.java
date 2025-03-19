package com.xhpolaris.essay_stateless.essay.core.score;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhpolaris.essay_stateless.essay.resp.EvaluateResponse;
import lombok.Data;

import java.util.List;

@Data
public class ScoreEvaluateResponse implements EvaluateResponse {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ScoreEvaluationResult ch;

    @Data
    private static class ScoreEvaluationResult {
        private Comments comments;
        private Content content;
        private Counting counting;
        private Expression expression;
        private Grammar grammar;
        private Handwriting handwriting;
        private Highlights highlights;
        private Relevance relevance;
        private Integer score;
        @JsonProperty("score_absolute")
        private Float scoreAbsolute;
        @JsonProperty("score_ori")
        private Integer scoreORI;
        @JsonProperty("score_str")
        private String scoreStr;
        @JsonProperty("score_str_ori")
        private String scoreStrORI;
    }

    @Data
    private static class Comments {
        @JsonProperty("paragraph_comments")
        private List<String> paragraphComments;
        @JsonProperty("passage_comments")
        private String passageComments;
    }

    @Data
    private static class Content {
        private String comments;
        private Integer score;
        @JsonProperty("score_str")
        private String scoreStr;

    }

    @Data
    private static class Counting {
        private Integer adj_adv_num;
        private Integer char_num;
        private Integer dieci_num;
        private Double fluency;
        private Integer grammar_mistake_num;
        private Integer highlight_sents_num;
        private Integer idiom_num;
        private Integer noun_type_num;
        private Integer para_num;
        private Integer sent_num;
        private Integer unique_word_num;
        private Integer verb_type_num;
        private Integer word_num;
        private Integer written_mistake_num;
    }

    @Data
    private static class Expression {
        private String comments;
        private Integer score;
        @JsonProperty("score_str")
        private String scoreStr;
    }

    @Data
    private static class Grammar {
        @JsonProperty("grammar_sentence")
        private List<String> grammarSentence;
        @JsonProperty("sick_sentence")
        private List<SickSentence> sickSentence;
        private List<Typo> typo;
        @JsonProperty("wording_error")
        private List<String> wordingError;
    }

    @Data
    private static class SickSentence {
        @JsonProperty("end_pos")
        private Integer endPos;
        private String ori;
        private Double score;
        private String revised;
        @JsonProperty("start_pos")
        private Integer startPos;
        private String type;
    }

    @Data
    private static class Typo {
        @JsonProperty("end_pos")
        private Integer endPos;
        private String extra;
        private String ori;
        private String revised;
        @JsonProperty("start_pos")
        private Integer startPos;
        private String type;
    }

    @Data
    private static class Handwriting {
        private String comments;
        private Integer score;
        @JsonProperty("score_str")
        private String scoreStr;
    }

    @Data
    private static class Highlights {
        @JsonProperty("advance_words")
        private List<WordHighlights> advanceWords;
        private List<String> descriptive;
        @JsonProperty("logic_words")
        private List<WordHighlights> logicWords;
        private List<WordHighlights> rhetoric;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    private static class WordHighlights {
        @JsonProperty("end_pos")
        private Integer endPos;
        private Memo memo;
        @JsonProperty("start_pos")
        private Integer startPos;
        private String type;
        private List<String> types;
    }

    @Data
    private static class Memo {
        private String text;
    }

    @Data
    private static class Relevance {
        private String comments;
        private Integer score;
        @JsonProperty("score_str")
        private String scoreStr;
    }

    @Override
    public String jsonString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            return "序列化失败";
        }
    }
}
