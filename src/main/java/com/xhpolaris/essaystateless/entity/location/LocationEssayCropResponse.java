/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-03-02
 * @Description:
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.entity.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xhpolaris.essaystateless.entity.resultCode.CommonCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationEssayCropResponse {
    @JsonProperty("image_base64")
    private String imageBase64;
}
