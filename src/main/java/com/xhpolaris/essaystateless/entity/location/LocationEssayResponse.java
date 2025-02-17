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
public class LocationEssayResponse {
    private String code;
    @JsonProperty("essay_box")
    private EssayBox essayBox;
    private String message;
}

