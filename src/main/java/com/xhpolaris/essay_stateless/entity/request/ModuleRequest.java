package com.xhpolaris.essay_stateless.entity.request;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class ModuleRequest {
    @Nullable
    public String title;
    public String essay;
}
