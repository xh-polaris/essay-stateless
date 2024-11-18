package com.xhpolaris.essaystateless.entity.request;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class ModuleRequest {
    @Nullable
    public String title;
    public String essay;
}
