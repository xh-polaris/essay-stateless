package com.xhpolaris.essay_stateless.essay.core.beta.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
public class APIOverall extends APICommon {
    // 总评内容
    public String comment;
    // 总评分数
    public Integer score;
}
