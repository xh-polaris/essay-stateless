package com.xhpolaris.essay_stateless.essay.core;

import com.xhpolaris.essay_stateless.essay.config.BetaConfig;
import com.xhpolaris.essay_stateless.essay.core.beta.api.*;
import com.xhpolaris.essay_stateless.essay.core.beta.BetaEvaluateResponse;
import com.xhpolaris.essay_stateless.essay.req.BetaEvaluateRequest;
import com.xhpolaris.essay_stateless.essay.req.BetaOcrEvaluateRequest;
import com.xhpolaris.essay_stateless.exception.BizException;
import com.xhpolaris.essay_stateless.exception.ECode;
import com.xhpolaris.essay_stateless.ocr.core.OcrCore;
import com.xhpolaris.essay_stateless.ocr.req.TitleOcrRequest;
import com.xhpolaris.essay_stateless.ocr.resp.TitleOcrResponse;
import com.xhpolaris.essay_stateless.utils.HttpClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
@Slf4j
public class EvaluateCore {
    private final HttpClient httpClient;
    private final OcrCore ocrCore;
    private final BetaConfig betaConfig;

    /**
     * 对应beta版接口 (beta为学校算法代号)
     */
    public BetaEvaluateResponse betaEvaluate(BetaEvaluateRequest req) throws Exception {

        // 初始化批改结果
        BetaEvaluateResponse response = new BetaEvaluateResponse(betaConfig.getModelVersion());

        // 作文基本信息，作为请求体
        Map<String, Object> _essay = new HashMap<>();
        _essay.put("title", req.title);
        _essay.put("essay", req.content);

        // 同步调用获取作文基本信息
        APIEssayInfo info = httpClient.syncCall(betaConfig.getApi().getEssayInfo(), APIEssayInfo.class, _essay, null);
        // 如果用户调用请求时携带了年级，则使用用户指定的年级
        if (req.grade != null) {
            info.grade = req.grade;
        }
        if (req.essayType != null) {
            info.essayType = req.essayType;
        }

        response.processEssayInfo(req.title, info);

        // 添加年纪信息和文体信息
        _essay.put("grade_int", info.grade);
        _essay.put("essay_type", info.essayType);

        // 用于记录调用耗时
        long start = System.currentTimeMillis();

        // 异步调用多个评估api，参数依次是url，返回的类型，文章内容map,header
        // 总评
        CompletableFuture<APIOverall> overall = httpClient.asyncCall(betaConfig.getApi().getOverall(), APIOverall.class, _essay, null);
        // 流畅度
        CompletableFuture<APIFluency> fluency = httpClient.asyncCall(betaConfig.getApi().getFluency(), APIFluency.class, _essay, null);
        // 好词好句
        CompletableFuture<APIWordSentence> wordSentence = httpClient.asyncCall(betaConfig.getApi().getWordSentence(), APIWordSentence.class, _essay, null);
        // 语法纠错
        CompletableFuture<APIGrammarInfo> grammarInfo = httpClient.asyncCall(betaConfig.getApi().getGrammarInfo(), APIGrammarInfo.class, _essay, null);
        // 表达
        CompletableFuture<APIExpression> expression = httpClient.asyncCall(betaConfig.getApi().getExpression(), APIExpression.class, _essay, null);
        // 修改建议
        CompletableFuture<APISuggestion> suggestion = httpClient.asyncCall(betaConfig.getApi().getSuggestion(), APISuggestion.class, _essay, null);
        // 段落点评
        CompletableFuture<APIParagraph> paragraph = httpClient.asyncCall(betaConfig.getApi().getParagraph(), APIParagraph.class, _essay, null);


        // 等待所有的异步任务完成
        CompletableFuture.allOf(grammarInfo, fluency, expression, suggestion, wordSentence, paragraph, overall).join();

        long end = System.currentTimeMillis();
        long totalTime = end - start;
        log.info("beta批改调用总耗时: {} 毫秒", totalTime);

        try {
            // 处理评估结果
            response.process(overall, fluency, wordSentence, grammarInfo, expression, suggestion, paragraph);
        } catch (Exception e) {
            throw new BizException(ECode.BIZ_ESSAY_DEFAULT);
        }
        // 返回
        return response;

    }

    /**
     * beta版带ocr的批改接口
     * 会返回标题
     */
    public BetaEvaluateResponse betaOcrEvaluate(BetaOcrEvaluateRequest req) throws Exception {
        // 构造ocr请求
        String imgType = req.getImageType() == null ? "base64" : req.getImageType();
        TitleOcrRequest ocrReq = new TitleOcrRequest(req.getImages(), req.getLeftType());
        String provider = req.getProvider() == null ? "bee" : req.getProvider();
        // 获取ocr识别结果
        TitleOcrResponse ocrResp = ocrCore.titleOcr(provider, imgType, ocrReq);

        // 构造批改请求
        BetaEvaluateRequest betaReq = new BetaEvaluateRequest(ocrResp.getTitle(), ocrResp.getContent(), req.getGrade(), req.getEssayType());
        return betaEvaluate(betaReq);
    }

//    /*
//     * 对应score接口
//     */
//
//    public ScoreEvaluateResponse evaluateScore(ScoreEvaluateRequest req) {
//        Map<String, Object> essay = new HashMap<>();
//        essay.put("title", req.getTitle());
//        essay.put("text", req.getText());
//        essay.put("ocr", req.getOcr());
//        essay.put("grade", req.getGrade());
//        essay.put("lang", req.getLang());
//
//        return httpClient.syncCall("score", ScoreEvaluateResponse.class, essay);
//    }
//    /*
//     * 调用下游算法，通用类型
//     */
//
//    public APICommonResponse commonModuleEvaluate(ModuleRequest req, String type) {
//        Map<String, Object> essay = new HashMap<>();
//
//        if (req.title != null)
//            essay.put("title", req.getTitle());
//
//        essay.put("essay", req.getEssay());
//        return switch (type) {
//            case "overall" -> httpClient.syncCall("overall", APICommonResponse.class, essay);
//            case "suggestion" -> httpClient.syncCall("suggestion", APICommonResponse.class, essay);
//            case "paragraph" -> httpClient.syncCall("paragraph", APICommonResponse.class, essay);
//            default -> null;
//        };
//    }
//    /*
//     * 调用下游算法，流畅度
//     */
//
//    public APIFluencyResponse fluencyModuleEvaluate(ModuleRequest req) {
//        Map<String, Object> essay = new HashMap<>();
//        if (req.title != null)
//            essay.put("title", req.getTitle());
//        essay.put("essay", req.getEssay());
//        return httpClient.syncCall("fluency", APIFluencyResponse.class, essay);
//    }
//    /*
//     * 调用下游算法，好词好句
//     */
//
//    public APIWordSentence wordSentenceModuleEvaluate(ModuleRequest req) {
//        Map<String, Object> essay = new HashMap<>();
//        if (req.title != null)
//            essay.put("title", req.getTitle());
//        essay.put("essay", req.getEssay());
//        return httpClient.syncCall("word_sentence", APIWordSentence.class, essay);
//    }
//    /*
//     * 调用下游算法，表达
//     */
//
//    public APIExpressionResponse expressionModuleEvaluate(ModuleRequest req) {
//        Map<String, Object> essay = new HashMap<>();
//        if (req.title != null)
//            essay.put("title", req.getTitle());
//        essay.put("essay", req.getEssay());
//        return httpClient.syncCall("expression", APIExpressionResponse.class, essay);
//    }
//
}
