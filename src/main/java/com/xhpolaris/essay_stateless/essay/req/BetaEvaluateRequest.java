package com.xhpolaris.essay_stateless.essay.req;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Beta版批改请求
 */
@Data
@AllArgsConstructor
public class BetaEvaluateRequest implements EvaluateRequest {
    // 作文标题
    public String title;
    // 作文内容
    public String content;
    // 年纪
    public Integer grade;

    @Override
    public String jsonString() {
        return String.format(
                "{\"title\":\"%s\",\"content\":\"%s\",\"grade\":%d}",
                title, content, grade
        );
    }
}
