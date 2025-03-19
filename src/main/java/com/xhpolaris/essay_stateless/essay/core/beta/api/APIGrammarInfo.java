package com.xhpolaris.essay_stateless.essay.core.beta.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * grammar_info 语法纠错
 */
@Getter
@Setter
public class APIGrammarInfo extends APICommon {
    // 语法纠错主体部分
    public GrammarInfo grammar;

    @Setter
    @Getter
    public static class GrammarInfo {
        // 单处语法错误
        public List<Typo> typo;
    }

    @Setter
    @Getter
    public static class Typo {
        // 原始错别字
        public String ori;
        // 修正后错别字
        public String revised;
        // 类型标签
        public String type;
        // 开始索引(全文索引)
        @JsonProperty("start_pos")
        public Integer startPos;
        // 结束索引(全文索引)
        @JsonProperty("end_pos")
        public Integer endPos;
    }
}
