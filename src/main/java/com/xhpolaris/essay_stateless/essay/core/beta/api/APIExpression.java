package com.xhpolaris.essay_stateless.essay.core.beta.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 逻辑表达
 */
@Getter
@Setter
public class APIExpression extends APICommon {
    // 表达评价
    public String comment;
    // 表达得分
    public Integer score;
}
