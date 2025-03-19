package com.xhpolaris.essay_stateless.essay.core.beta.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * word_sentence 好词好句
 */
@Getter
@Setter
public class APIWordSentence extends APICommon {
    // 好词好句得分
    public Integer score;
    // 好词好句评价
    public String comment;
    // 好词好句主体部分
    public WordSentenceData data;

    @Getter
    @Setter
    public static class WordSentenceData {
        public WordSentenceResult results;
    }

    @Getter
    @Setter
    public static class WordSentenceResult {
        // 好词
        @JsonProperty("good_words")
        public List<GoodWordResult> goodWords;
        // 好句
        @JsonProperty("good_sents")
        public List<GoodSentenceResult> goodSents;
    }

    @Getter
    @Setter
    public static class GoodWordResult {
        // 段落索引
        @JsonProperty("paragraph_id")
        public Integer paragraphId;
        // 句子索引
        @JsonProperty("sent_id")
        public Integer sentId;
        // 开始索引
        public Integer start;
        // 结束索引
        public Integer end;
    }

    @Getter
    @Setter
    public static class GoodSentenceResult {
        // 段落索引
        @JsonProperty("paragraph_id")
        public Integer paragraphId;
        // 句子索引
        @JsonProperty("sent_id")
        public Integer sentId;
        // 修辞手法
        public String label;
    }
}
