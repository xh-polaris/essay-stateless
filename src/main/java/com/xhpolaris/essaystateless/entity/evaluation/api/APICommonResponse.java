package com.xhpolaris.essaystateless.entity.evaluation.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APICommonResponse{
    private Integer code;
    private String message;
    private String comment;
    private List<String> comments;
    private Integer device;
    private Integer score;
    private List<List<String>>sentences;

}