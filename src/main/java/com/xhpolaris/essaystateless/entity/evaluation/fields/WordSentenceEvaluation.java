package com.xhpolaris.essaystateless.entity.evaluation.fields;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class WordSentenceEvaluation {
    public int wordSentenceScore;
    public String wordSentenceDescription;
    public List<List<SentenceEvaluation>> sentenceEvaluations = new ArrayList<>();

    @Data
    public static class SentenceEvaluation {
        public Boolean isGoodSentence;
        public int label;
        public List<WordEvaluation> wordEvaluations = new ArrayList<>();
        public Map<String, String> type = new HashMap<>();
    }

    @Data
    public static class WordEvaluation {
        public List<Integer> span = new ArrayList<>();
        public Map<String, String> type = new HashMap<>();
    }

    @Data
    @Builder
    public static class Type {
        public String level1;
        public String level2;
    }
}
