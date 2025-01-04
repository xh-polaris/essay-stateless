package com.xhpolaris.essaystateless.entity.request;


import lombok.Data;

import java.util.List;

@Data
public class BeeOcrRequest {
    private List<String> images;
}
