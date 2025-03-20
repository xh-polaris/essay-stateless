package com.xhpolaris.essay_stateless.essay.core.beta.fields;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字词评价
 */
@NoArgsConstructor
public class WordSentenceEvaluation {
    // 好词好句评分
    public int wordSentenceScore;
    // 好词好句描述
    public String wordSentenceDescription;
    // 句子点评 - 依据段-句划分
    public List<List<SentenceEvaluation>> sentenceEvaluations = new ArrayList<>();

    @Data
    public static class SentenceEvaluation {
        @JsonProperty("isGoodSentence")
        // 是否是好句
        public boolean isGoodSentence;
        // 修辞标签
        public String label;
        // 类型
        public Map<String, String> type = new HashMap<>();
        // 句中词评价
        public List<WordEvaluation> wordEvaluations = new ArrayList<>();
    }

    @Data
    public static class WordEvaluation {
        // 开始索引于结束索引
        public List<Integer> span = new ArrayList<>();
        // 原错误
        public String ori;
        // 修改后
        public String revised;
        // 错误类型标签 level1: 还需努力; level2: 具体错误
        public Map<String, String> type = new HashMap<>();
    }

}
