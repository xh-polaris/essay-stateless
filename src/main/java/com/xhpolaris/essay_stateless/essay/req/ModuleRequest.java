package com.xhpolaris.essay_stateless.essay.req;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class ModuleRequest implements EvaluateRequest {
    public String title;
    public String essay;

    @Override
    public String jsonString() {
        return String.format("{\"title\":\"%s\",\"essay\":\"%s\"}", title, essay);
    }
}
