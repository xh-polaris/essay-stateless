package com.xhpolaris.essay_stateless.essay.core.beta.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * fluency 流畅度
 */
@Getter
@Setter
public class APIFluency extends APICommon {
    // 流畅度得分
    public Integer score;
    // 流畅度评价
    public String comment;
    // fluency批改主体部分
    public FluencyData data;

    @Getter
    @Setter
    public static class FluencyData {
        // fluency批改结果
        public FluencyResult results;
    }

    @Getter
    @Setter
    public static class FluencyResult {
        // 流畅度得分
        public Integer score;
        // 病句 TODO 应该由算法端合并到grammar
        @JsonProperty("sick_sents")
        public List<SickSentence> sickSentences;
    }

    @Getter
    @Setter
    public static class SickSentence {
        // 粗略的标签
        @JsonProperty("coarse_label")
        public String coarseLabel;
        // 标签
        public String label;
        // 段落id
        @JsonProperty("paragraph_id")
        public Integer paragraphId;
        // 句子id
        @JsonProperty("sent_id")
        public Integer sentenceId;
    }
}
