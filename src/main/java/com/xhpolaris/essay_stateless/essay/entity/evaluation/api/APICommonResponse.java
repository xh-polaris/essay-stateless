package com.xhpolaris.essay_stateless.essay.entity.evaluation.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APICommonResponse {
    private Integer code;
    private String message;
    private String comment;
    private List<String> comments;
    private Integer device;
    private Integer score;
    private List<List<String>> sentences;

}