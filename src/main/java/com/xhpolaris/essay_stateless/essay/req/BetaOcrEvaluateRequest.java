package com.xhpolaris.essay_stateless.essay.req;

import lombok.Data;

import java.util.List;

@Data
public class BetaOcrEvaluateRequest implements EvaluateRequest {
    private List<String> images;

    private Integer grade;

    private String leftType;

    private String imageType;

    @Override
    public String jsonString() {
        StringBuilder json = new StringBuilder();
        json.append("{");

        // 拼接 images 字段
        json.append("\"images\":[");
        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                json.append("\"").append(images.get(i)).append("\"");
                if (i < images.size() - 1) {
                    json.append(",");
                }
            }
        }
        json.append("]");

        // 拼接 grade 字段
        json.append(",\"grade\":");
        if (grade != null) {
            json.append(grade);
        } else {
            json.append("null");
        }

        json.append("}");
        return json.toString();
    }
}
