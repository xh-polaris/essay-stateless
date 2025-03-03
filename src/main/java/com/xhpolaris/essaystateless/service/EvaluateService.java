package com.xhpolaris.essaystateless.service;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.xhpolaris.essaystateless.entity.evaluation.EvaluationResponse;
import com.xhpolaris.essaystateless.entity.evaluation.api.*;
import com.xhpolaris.essaystateless.entity.evaluation.fields.ModelVersion;
import com.xhpolaris.essaystateless.entity.request.ModuleRequest;
import com.xhpolaris.essaystateless.entity.request.ScoreEvaluationRequest;
import com.xhpolaris.essaystateless.entity.scoreEvaluation.ScoreEvaluationResponse;
import com.xhpolaris.essaystateless.utils.BeeOcrUtil;
import com.xhpolaris.essaystateless.utils.HttpClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
@Slf4j
public class EvaluateService {

    private final HttpClient httpClient;
    private final BeeOcrUtil beeOcrUtil;

    private final ModelVersion mv;

    /**
     * 对应beta版接口 (beta为学校算法代号)
     */
    public EvaluationResponse evaluate(String title, String content, Integer grade) {
        // 将标题和作文转成简体中文
        title = ZhConverterUtil.toSimple(title);
        content = ZhConverterUtil.toSimple(content);

        Map<String, Object> essay = new HashMap<>();
        essay.put("title", title);
        essay.put("essay", content);

        APIEssayInfoResponse info = httpClient.syncCall("essay_info", APIEssayInfoResponse.class, essay);
        if (grade != null) {
            info.gradeInt = grade;
        }

        essay.put("grade_int", info.gradeInt);
        essay.put("essay_type", info.essayType);

        long start = System.currentTimeMillis();

        // 异步调用多个评估api，参数依次是url，返回的类型，文章内容map
        CompletableFuture<APICommonResponse> overall = httpClient.asyncCall("overall", APICommonResponse.class, essay);
        CompletableFuture<APIFluencyResponse> fluency = httpClient.asyncCall("fluency", APIFluencyResponse.class, essay);
        CompletableFuture<APIWordSentenceResponse> wordSentence = httpClient.asyncCall("word_sentence", APIWordSentenceResponse.class, essay);
        //CompletableFuture<APIBadWordResponse> badWords = httpClient.asyncCall("cgec", APIBadWordResponse.class, essay);
        CompletableFuture<APIGrammarInfoResponse> grammarInfo = httpClient.asyncCall("grammar_info", APIGrammarInfoResponse.class, essay);
        CompletableFuture<APIExpressionResponse> expression = httpClient.asyncCall("expression", APIExpressionResponse.class, essay);
        CompletableFuture<APICommonResponse> suggestion = httpClient.asyncCall("suggestion", APICommonResponse.class, essay);
        CompletableFuture<APICommonResponse> paragraph = httpClient.asyncCall("paragraph", APICommonResponse.class, essay);


        // 等待所有的异步任务完成
        CompletableFuture.allOf(grammarInfo, fluency, expression,
                suggestion, wordSentence,
                paragraph, overall).join();

        long end = System.currentTimeMillis();

        long totalTime = end - start;
        log.info("异步调用总耗时: {} 毫秒", totalTime);

        EvaluationResponse response = new EvaluationResponse(mv);
        response.setTitle(title);

        // 处理评估结果
        //response.process(overall, fluency, wordSentence, badWords, expression, suggestion, paragraph);
        response.process(overall, fluency, wordSentence, grammarInfo, expression, suggestion, paragraph);
        // 返回
        return response;

    }

    /**
     * beta版带ocr的批改接口
     */
    public EvaluationResponse betaOcrEvaluate(List<String> images, Integer grade) throws Exception {
        // 保留类型为全部
        List<String> result = beeOcrUtil.OcrAll(images, "base64");
        return evaluate(result.get(0), result.get(1), grade);
    }

    /*
     * 对应score接口
     */

    public ScoreEvaluationResponse evaluateScore(ScoreEvaluationRequest req) {
        Map<String, Object> essay = new HashMap<>();
        essay.put("title", req.getTitle());
        essay.put("text", req.getText());
        essay.put("ocr", req.getOcr());
        essay.put("grade", req.getGrade());
        essay.put("lang", req.getLang());

        return httpClient.syncCall("score", ScoreEvaluationResponse.class, essay);
    }
    /*
     * 调用下游算法，通用类型
     */

    public APICommonResponse commonModuleEvaluate(ModuleRequest req, String type) {
        Map<String, Object> essay = new HashMap<>();

        if (req.title != null)
            essay.put("title", req.getTitle());

        essay.put("essay", req.getEssay());
        return switch (type) {
            case "overall" -> httpClient.syncCall("overall", APICommonResponse.class, essay);
            case "suggestion" -> httpClient.syncCall("suggestion", APICommonResponse.class, essay);
            case "paragraph" -> httpClient.syncCall("paragraph", APICommonResponse.class, essay);
            default -> null;
        };
    }
    /*
     * 调用下游算法，流畅度
     */

    public APIFluencyResponse fluencyModuleEvaluate(ModuleRequest req) {
        Map<String, Object> essay = new HashMap<>();
        if (req.title != null)
            essay.put("title", req.getTitle());
        essay.put("essay", req.getEssay());
        return httpClient.syncCall("fluency", APIFluencyResponse.class, essay);
    }
    /*
     * 调用下游算法，好词好句
     */

    public APIWordSentenceResponse wordSentenceModuleEvaluate(ModuleRequest req) {
        Map<String, Object> essay = new HashMap<>();
        if (req.title != null)
            essay.put("title", req.getTitle());
        essay.put("essay", req.getEssay());
        return httpClient.syncCall("word_sentence", APIWordSentenceResponse.class, essay);
    }
    /*
     * 调用下游算法，文本检错
     */

    public APIBadWordResponse badWordModuleEvaluate(ModuleRequest req) {
        Map<String, Object> essay = new HashMap<>();
        if (req.title != null)
            essay.put("title", req.getTitle());
        essay.put("essay", req.getEssay());
        return httpClient.syncCall("cgec", APIBadWordResponse.class, essay);
    }
    /*
     * 调用下游算法，表达
     */

    public APIExpressionResponse expressionModuleEvaluate(ModuleRequest req) {
        Map<String, Object> essay = new HashMap<>();
        if (req.title != null)
            essay.put("title", req.getTitle());
        essay.put("essay", req.getEssay());
        return httpClient.syncCall("expression", APIExpressionResponse.class, essay);
    }
    
}
