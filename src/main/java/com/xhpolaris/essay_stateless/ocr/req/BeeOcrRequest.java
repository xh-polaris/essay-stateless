package com.xhpolaris.essay_stateless.ocr.req;


import lombok.Data;

import java.util.List;

@Data
public class BeeOcrRequest {
    private List<String> images;
}
