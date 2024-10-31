package com.xhpolaris.essaystateless.service;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.xhpolaris.essaystateless.entity.evaluation.EvaluationResponse;
import com.xhpolaris.essaystateless.entity.evaluation.api.*;
import com.xhpolaris.essaystateless.entity.evaluation.fields.ModelVersion;
import com.xhpolaris.essaystateless.utils.HttpClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class EvaluateService {

    private final HttpClient httpClient;

    private final ModelVersion mv;

    public EvaluationResponse evaluate(String title, String content) {
        // 将标题和作文转成简体中文
        title = ZhConverterUtil.toSimple(title);
        content = ZhConverterUtil.toSimple(content);

        Map<String, String> essay = new HashMap<>();
        essay.put("title", title);
        essay.put("essay", content);

        long start = System.currentTimeMillis();

        // 异步调用多个评估api，参数依次是url，返回的类型，文章内容map
        CompletableFuture<APICommonResponse> overall = asyncCall("general_comment", APICommonResponse.class, essay);
        CompletableFuture<APIFluencyResponse> fluency = asyncCall("fluency_comment", APIFluencyResponse.class, essay);
        CompletableFuture<APIWordSentenceResponse> wordSentence = asyncCall("word_sentence_comment", APIWordSentenceResponse.class, essay);
        CompletableFuture<APIBadWordResponse> badWords = asyncCall("cgec", APIBadWordResponse.class, essay);
        CompletableFuture<APIExpressionResponse> expression = asyncCall("expression_comment", APIExpressionResponse.class, essay);
        CompletableFuture<APICommonResponse> suggestion = asyncCall("suggestion", APICommonResponse.class, essay);
        CompletableFuture<APICommonResponse> paragraph = asyncCall("paragraph_comment", APICommonResponse.class, essay);


        // 等待所有的异步任务完成
        CompletableFuture.allOf(badWords, fluency, expression,
                suggestion, wordSentence,
                paragraph, overall).join();

        long end = System.currentTimeMillis();

        long totalTime = end - start;
        System.out.println("异步调用总耗时: " + totalTime + " 毫秒");

        EvaluationResponse response = new EvaluationResponse(mv);
        response.setTitle(title);

        // 处理评估结果
        response.process(overall, fluency, wordSentence, badWords, expression, suggestion, paragraph);

        // 返回
        return response;

    }

    private <T> CompletableFuture<T> asyncCall(String url, Class<T> responseClass, Map<String, String> essay) {
        return CompletableFuture.supplyAsync(() -> httpClient.postForEntity(
                httpClient.buildEvaluateURL(url), responseClass, essay));
    }
}
