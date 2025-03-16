package com.xhpolaris.essay_stateless.essay.entity.evaluation.fields;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ModelVersion {

    @Value("${model-version.name}")
    public String name;

    @Value("${model-version.version}")
    public String version;

}
