package com.xhpolaris.essay_stateless.essay.controller;

import com.xhpolaris.essay_stateless.essay.annotation.RawLog;
import com.xhpolaris.essay_stateless.essay.req.BetaOcrEvaluateRequest;
import com.xhpolaris.essay_stateless.essay.req.BetaEvaluateRequest;
import com.xhpolaris.essay_stateless.essay.core.EvaluateCore;
import com.xhpolaris.essay_stateless.re.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evaluate")
@AllArgsConstructor
public class EvaluateController {

    private final EvaluateCore evaluateService;

    /**
     * beta版批改接口
     */
    @PostMapping
    @RawLog("/evaluate")
    public Response betaEvaluate(@RequestBody BetaEvaluateRequest req) throws Exception {
        return Response.Succeed(evaluateService.betaEvaluate(req));
    }

    /**
     * beta版批改 - ocr
     */
    @PostMapping("/beta/ocr")
    @RawLog("/evaluate/beta/ocr")
    public Response betaOcrEvaluate(@RequestBody BetaOcrEvaluateRequest req) throws Exception {
        return Response.Succeed(evaluateService.betaOcrEvaluate(req));
    }

//    /** 暂时没有在用的，先注释掉
//     * score版接口
//     */
//    @PostMapping("/score")
//    @RawLog("/evaluate/score")
//    public ScoreEvaluateResponse scoreEvaluate(@RequestBody ScoreEvaluateRequest req) throws Exception {
//        return evaluateService.evaluateScore(req);
//    }
}
