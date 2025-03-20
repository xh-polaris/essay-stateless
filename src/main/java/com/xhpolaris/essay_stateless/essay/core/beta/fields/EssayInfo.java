package com.xhpolaris.essay_stateless.essay.core.beta.fields;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作文基本信息
 */
@Data
@NoArgsConstructor
public class EssayInfo {
    // 作文类型
    public String essayType;
    // 年级评估
    public Integer grade;
    // 作文统计信息
    public Counting counting;

    @Data
    @AllArgsConstructor
    public static class Counting {
        // 形容词副词数量
        public Integer adjAdvNum;
        // 字符总数
        public Integer charNum;
        // 叠词数量
        public Integer dieciNum;
        // 流畅度分数（GPT-2 打分）
        public Integer fluency;
        // 语法错误数
        public Integer grammarMistakeNum;
        // 好句数量
        public Integer highlightSentsNum;
        // 成语数量
        public Integer idiomNum;
        // 名词数量
        public Integer nounTypeNum;
        // 段落数量
        public Integer paraNum;
        // 句子数量
        public Integer sentNum;
        // 不同词的数量
        public Integer uniqueWordNum;
        // 动词数量
        public Integer verbTypeNum;
        // 词数
        public Integer wordNum;
        // 书写错误数
        public Integer writtenMistakeNum;
    }
}
