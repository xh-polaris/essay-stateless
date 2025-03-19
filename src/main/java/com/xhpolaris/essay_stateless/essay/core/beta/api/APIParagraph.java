package com.xhpolaris.essay_stateless.essay.core.beta.api;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 段落点评
 */
@Getter
@Setter
public class APIParagraph {
    // 段落点评数组
    public List<String> comments;
}
