package com.xhpolaris.essaystateless.entity.evaluation.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class APIEssayInfoResponse extends APICommonResponse {
    @JsonProperty("grade_int")
    public Integer gradeInt;

    @JsonProperty("essay_type")
    public String essayType;
}
