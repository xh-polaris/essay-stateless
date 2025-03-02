/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-02-11
 * @Description: 作文范围定位response
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.entity.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LocationEssayResponse {
    // 服务器返回的String类型code
    private String code;
    @JsonProperty("essay_box")
    private EssayBox essayBox;
    private String message;

    @Getter
    @Setter
    public static class EssayBox {
        /**
         * 定位后高度
         */
        private double height;
        /**
         * 定位，四个坐标依次是左上下x1,y1，右下x2,y2
         */
        private double[][] location;
        /**
         * 定位后宽度
         */
        private double width;
    }
}

