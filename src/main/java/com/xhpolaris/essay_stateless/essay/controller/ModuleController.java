//package com.xhpolaris.essay_stateless.essay.controller;
//
//import com.xhpolaris.essay_stateless.essay.core.beta.api.*;
//import com.xhpolaris.essay_stateless.essay.req.ModuleRequest;
//import com.xhpolaris.essay_stateless.essay.core.EvaluateService;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/module")
//@AllArgsConstructor
//public class ModuleController {
//
//    private final EvaluateService evaluateService;
//
//    /**
//     * 总评、修改建议、段落
//     */
//    @PostMapping("/common/{type}")
//    public APICommonResponse overallEvaluate(@RequestBody ModuleRequest req, @PathVariable String type) throws Exception {
//        APICommonResponse response = evaluateService.commonModuleEvaluate(req, type);
//        if (response == null)
//            throw new Exception("调用失败，请重试");
//        return response;
//    }
//
//    /*
//     * 流畅度
//     */
//    @PostMapping("/fluency")
//    public APIFluencyResponse fluencyEvaluate(@RequestBody ModuleRequest req) throws Exception {
//        APIFluencyResponse response = evaluateService.fluencyModuleEvaluate(req);
//        if (response == null)
//            throw new Exception("调用失败，请重试");
//        return response;
//    }
//
//    /*
//     * 好词好句
//     */
//    @PostMapping("/word_sentence")
//    public APIWordSentence wordSentenceEvaluate(@RequestBody ModuleRequest req) throws Exception {
//        APIWordSentence response = evaluateService.wordSentenceModuleEvaluate(req);
//        if (response == null)
//            throw new Exception("调用失败，请重试");
//        return response;
//    }
//
//    /*
//     * 表达
//     */
//    @PostMapping("/expression")
//    public APIExpressionResponse expressionEvaluate(@RequestBody ModuleRequest req) throws Exception {
//        APIExpressionResponse response = evaluateService.expressionModuleEvaluate(req);
//        if (response == null)
//            throw new Exception("调用失败，请重试");
//        return response;
//    }
//}
