package com.xhpolaris.essay_stateless.entity.request;


import lombok.Data;

import java.util.List;

@Data
public class BeeOcrRequest {
    private List<String> images;
}
