package com.xhpolaris.essay_stateless.ocr.core.provider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhpolaris.essay_stateless.exception.BizException;
import com.xhpolaris.essay_stateless.exception.ECode;
import com.xhpolaris.essay_stateless.ocr.config.BeeOcrConfig;
import com.xhpolaris.essay_stateless.ocr.req.DefaultOcrRequest;
import com.xhpolaris.essay_stateless.ocr.req.TitleOcrRequest;
import com.xhpolaris.essay_stateless.ocr.resp.DefaultOcrResponse;
import com.xhpolaris.essay_stateless.ocr.resp.TitleOcrResponse;
import com.xhpolaris.essay_stateless.utils.HttpClient;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;


import javax.swing.text.html.Option;
import java.util.*;

@Component
@AllArgsConstructor
public class BeeOcrProvider implements OcrProvider {

    private final BeeOcrConfig config;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static Map<String, String> headers = null;

    @Override
    public DefaultOcrResponse ocr(String imgType, DefaultOcrRequest req) throws Exception {

        // 图片,保留类型
        List<String> images = req.getImages();
        String leftType = (Optional.ofNullable(req.getLeftType()).orElse("all"));

        // 识别图片
        List<String> result = _ocr(images, imgType, leftType);

        // 组合结果, 默认Ocr不带标题
        String content = Strings.join(result, '\n');
        return new DefaultOcrResponse(null, content);
    }

    @Override
    public TitleOcrResponse titleOcr(String imgType, TitleOcrRequest req) throws Exception {

        // 图片,保留类型
        List<String> images = req.getImages();
        String leftType = (Optional.ofNullable(req.getLeftType()).orElse("all"));

        // 识别图片
        List<String> result = _ocr(images, imgType, leftType);

        // 获取标题和内容
        String title = result.isEmpty() ? "" : result.get(0);
        String content = result.size() <= 1 ? "" : Strings.join(result.subList(1, result.size()), '\n');

        return new TitleOcrResponse(title, content);
    }

    private List<String> _ocr(List<String> images, String imgType, String leftType) throws Exception {
        // 构造请求头
        if (headers == null) {
            headers = Map.of("x-app-secret", config.getXAppKey(), "x-app-key", config.getXAppSecret());
        }

        // 识别结果
        List<String> results = new ArrayList<>();

        // 图片,保留类型,图片类型参数
        String imageParam = "image_" + imgType;

        // 识别每一张图片
        for (String image : images) {
            ocrOne(results, config.getOcr(), image, imageParam, leftType);
        }
        return results;
    }

    private void ocrOne(List<String> results, String url, String image, String imageParam, String leftType) throws Exception {
        // 构建请求体
        Map<String, Object> body = Map.of(imageParam, image);

        // 调用Ocr
        String _response = httpClient.postForEntity(url, String.class, body, headers);

        try {
            Map<String, Object> _resp = objectMapper.readValue(_response, new TypeReference<>() {
            });

            // 校验调用是否成功
            if ((int) _resp.get("code") != 0)
                throw new BizException(ECode.BIZ_OCR_DEFAULT);

            // 获取响应中的数据部分
            Map<String, Object> data = objectMapper.convertValue(_resp.get("data"), new TypeReference<Map<String, Object>>() {
            });
            // 获取识别的每一行
            List<Map<String, Object>> lines = objectMapper.convertValue(data.get("lines"), new TypeReference<List<Map<String, Object>>>() {
            });
            // 获取识别的每一个区域
            List<Map<String, Object>> areas = objectMapper.convertValue(data.get("areas"), new TypeReference<List<Map<String, Object>>>() {
            });

            // 获取需要排除的
            int exclude = switch (leftType) {
                case "handwriting" -> 0;
                case "print" -> 1;
                default -> -1;
            };

            // 排除的部分
            Set<Integer> excludes = new HashSet<>();

            if (exclude != -1) {
                // 过滤掉需要排除的部分
                lines.stream()
                        .filter(line -> (int) line.get("handwritten") == exclude)
                        .forEach(line -> excludes.add((Integer) line.get("area_index")));
            }

            // 添加剩余的非空部分
            areas.stream()
                    .filter(area -> !excludes.contains((Integer) area.get("index")))
                    .map(area -> (String) area.get("text"))
                    .filter(text -> !text.isEmpty())
                    .forEach(results::add);

        } catch (Exception e) {
            // 这里没有必要过于区分，拿不到结果都是因为第三方问题
            throw new BizException(ECode.BIZ_OCR_DEFAULT);
        }
    }
}
