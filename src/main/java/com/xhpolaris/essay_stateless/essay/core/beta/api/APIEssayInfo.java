package com.xhpolaris.essay_stateless.essay.core.beta.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xhpolaris.essay_stateless.essay.core.beta.fields.EssayInfo;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class APIEssayInfo extends APICommon {
    // 年纪
    @JsonProperty("grade_int")
    public Integer grade;

    // 作文类型
    @JsonProperty("essay_type")
    public String essayType;

    // 作文统计信息
    public Counting counting;

    // 段-句划分
    public List<List<String>> sents;

    @Data
    public static class Counting {
        // 形容词副词数量
        @JsonProperty("adj_adv_num")
        public Integer adjAdvNum;
        // 字符总数
        @JsonProperty("char_num")
        public Integer charNum;
        // 叠词数量
        @JsonProperty("dieci_num")
        public Integer dieciNum;
        // 流畅度分数（GPT-2 打分）
        @JsonProperty("fluency")
        public Integer fluency;
        // 语法错误数
        @JsonProperty("grammar_mistake_num")
        public Integer grammarMistakeNum;
        // 好句数量
        @JsonProperty("highlight_sents_num")
        public Integer highlightSentsNum;
        // 成语数量
        @JsonProperty("idiom_num")
        public Integer idiomNum;
        // 名词数量
        @JsonProperty("noun_type_num")
        public Integer nounTypeNum;
        // 段落数量
        @JsonProperty("para_num")
        public Integer paraNum;
        // 句子数量
        @JsonProperty("sent_num")
        public Integer sentNum;
        // 不同词的数量
        @JsonProperty("unique_word_num")
        public Integer uniqueWordNum;
        // 动词数量
        @JsonProperty("verb_type_num")
        public Integer verbTypeNum;
        // 词数
        @JsonProperty("word_num")
        public Integer wordNum;
        // 书写错误数
        @JsonProperty("written_mistake_num")
        public Integer writtenMistakeNum;
    }
}