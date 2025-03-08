package com.xhpolaris.essaystateless.controller;

import com.xhpolaris.essaystateless.entity.evaluation.api.*;
import com.xhpolaris.essaystateless.entity.request.ModuleRequest;
import com.xhpolaris.essaystateless.entity.result.ResponseResult;
import com.xhpolaris.essaystateless.exception.ExceptionCode;
import com.xhpolaris.essaystateless.exception.BizException;
import com.xhpolaris.essaystateless.service.EvaluateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/module")
@AllArgsConstructor
public class ModuleController {

    private final EvaluateService evaluateService;

    /*
     * 总评、修改建议、段落
     */
    @PostMapping("/common/{type}")
    public ResponseResult<APICommonResponse> overallEvaluate(@RequestBody ModuleRequest req, @PathVariable String type) throws Exception {
        APICommonResponse response = evaluateService.commonModuleEvaluate(req, type);
        if (response == null)
            throw new BizException(ExceptionCode.MODULE_SERVER_ERROR);
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }

    /*
     * 流畅度
     */
    @PostMapping("/fluency")
    public ResponseResult<APIFluencyResponse> fluencyEvaluate(@RequestBody ModuleRequest req) throws Exception {
        APIFluencyResponse response = evaluateService.fluencyModuleEvaluate(req);
        if (response == null)
            throw new BizException(ExceptionCode.MODULE_SERVER_ERROR);
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }

    /*
     * 好词好句
     */
    @PostMapping("/word_sentence")
    public ResponseResult<APIWordSentenceResponse> wordSentenceEvaluate(@RequestBody ModuleRequest req) throws Exception {
        APIWordSentenceResponse response = evaluateService.wordSentenceModuleEvaluate(req);
        if (response == null)
            throw new BizException(ExceptionCode.MODULE_SERVER_ERROR);
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }

    /*
     * 文本检错
     */
    @PostMapping("/cgec")
    public ResponseResult<APIBadWordResponse> badWordEvaluate(@RequestBody ModuleRequest req) throws Exception {
        APIBadWordResponse response = evaluateService.badWordModuleEvaluate(req);
        if (response == null)
            throw new BizException(ExceptionCode.MODULE_SERVER_ERROR);
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }

    /*
     * 表达
     */
    @PostMapping("/expression")
    public ResponseResult<APIExpressionResponse> expressionEvaluate(@RequestBody ModuleRequest req) throws Exception {
        APIExpressionResponse response = evaluateService.expressionModuleEvaluate(req);
        if (response == null)
            throw new BizException(ExceptionCode.MODULE_SERVER_ERROR);
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }
}
