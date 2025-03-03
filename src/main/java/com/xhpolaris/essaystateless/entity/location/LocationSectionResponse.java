/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-02-11
 * @Description: 作文范围定位response
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.entity.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LocationSectionResponse {
    private String code;
    private String message;
    @JsonProperty("section_box")
    private double[][] sectionBox;
}