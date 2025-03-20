package com.xhpolaris.essay_stateless.essay.core.beta.fields;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ModelVersion {

    // 模型名称
    public String name;
    // 模型版本
    public String version;

}
