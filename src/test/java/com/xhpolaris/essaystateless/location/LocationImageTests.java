/**
 * @Author: Zephyrtoria
 * @CreateTime: 2025-02-16
 * @Description: 作文图片定位测试
 * @Version: 1.0
 */

package com.xhpolaris.essaystateless.location;

import com.xhpolaris.essaystateless.utils.ImageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
public class LocationImageTests {

    @Autowired
    private ImageUtil imageUtil;

    @Test
    public void testCropImage() throws IOException {
        String filePath = "src\\test\\java\\com\\xhpolaris\\essaystateless\\location\\image.txt";
        BufferedImage image = imageUtil.base64ToImage(Files.readString(Paths.get(filePath)));

        // 裁剪
        image = imageUtil.cropImage(image, 10,10, 1000, 1000);
        imageUtil.saveImage(image, "src\\test\\java\\com\\xhpolaris\\essaystateless\\location\\image.png");
    }
}
