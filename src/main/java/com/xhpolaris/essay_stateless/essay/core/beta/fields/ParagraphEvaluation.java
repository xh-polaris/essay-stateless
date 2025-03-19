package com.xhpolaris.essay_stateless.essay.core.beta.fields;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 段落点评
 */
@Data
@AllArgsConstructor
public class ParagraphEvaluation {
    // 段落索引
    public int paragraphIndex;
    // 段落评价
    public String content;
}
