/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-02-16
 * @Description: 图片处理工具类
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Component
@AllArgsConstructor
@Slf4j
public class ImageUtil {
    /**
     * 解码Base64编码图片
     *
     * @param imageBase64
     * @return
     * @throws IOException
     */
    public BufferedImage base64ToImage(String imageBase64) throws IOException {
        // 去除base64前缀
        imageBase64 = imageBase64.substring(imageBase64.indexOf(",", 1) + 1);
        // 解码
        byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(bis);
    }

    /**
     * 使用Base64编码图片
     *
     * @param image
     * @return
     */
    public String imageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", stream);
        String base64 = Base64.getEncoder().encodeToString(stream.toByteArray());
        return "data:image/jpg;base64," + base64;
    }

    /**
     * 裁剪图片
     * @param originalImage
     * @param x1 左上角顶点x坐标
     * @param y1 左上角顶点y坐标
     * @param x2 右下角顶点x坐标
     * @param y2 右下角顶点y坐标
     * @return
     */
    public BufferedImage cropImage(BufferedImage originalImage, int x1, int y1, int x2, int y2) {
        int weight = x2 - x1, height = y2 - y1;
        // 裁剪
        BufferedImage croppedImage = new BufferedImage(weight, height, BufferedImage.TYPE_INT_ARGB);
        croppedImage.getGraphics().drawImage(originalImage, 0, 0, weight, height, x1, y1, x2, y2, null);
        return croppedImage;
    }

    public void saveImage(BufferedImage image, String filePath) throws IOException {
        File outputFile = new File(filePath);
        ImageIO.write(image, "png", outputFile);
    }
}
