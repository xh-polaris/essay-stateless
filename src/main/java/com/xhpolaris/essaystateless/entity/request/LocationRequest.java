/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-02-11
 * @Description: 作文范围定位request
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LocationRequest {
    @JsonProperty("image_base64")
    private String imageBase64;
}
