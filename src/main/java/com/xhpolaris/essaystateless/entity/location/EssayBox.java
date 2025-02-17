/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-02-17
 * @Description:
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.entity.location;

import lombok.Data;

@Data
public class EssayBox {
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