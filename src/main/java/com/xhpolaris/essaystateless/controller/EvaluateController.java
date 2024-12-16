package com.xhpolaris.essaystateless.controller;

import com.xhpolaris.essaystateless.entity.evaluation.EvaluationResponse;
import com.xhpolaris.essaystateless.entity.request.BetaOcrEvaluateRequest;
import com.xhpolaris.essaystateless.entity.request.EvaluateRequest;
import com.xhpolaris.essaystateless.entity.request.ScoreEvaluationRequest;
import com.xhpolaris.essaystateless.entity.scoreEvaluation.ScoreEvaluationResponse;
import com.xhpolaris.essaystateless.service.EvaluateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evaluate")
@AllArgsConstructor
public class EvaluateController {

    private final EvaluateService evaluateService;

    /*
     * beta版批改(学校)
     */
    @PostMapping
    public EvaluationResponse evaluate(@RequestBody EvaluateRequest req) throws Exception {
        EvaluationResponse response = evaluateService.evaluate(req.title, req.content);
        if (response == null) {
            throw new Exception("评估失败，请重试");
        }
        return response;
    }

    /*
     * beta版ocr批改
     */
    @PostMapping("/beta/ocr")
    public EvaluationResponse betaOcrEvaluate(@RequestBody BetaOcrEvaluateRequest req) throws Exception {
        EvaluationResponse response = evaluateService.betaOcrEvaluate(req.getImages());
        if (response == null) {
            throw new Exception("批改失败，请重试");
        }
        return response;
    }

    /*
     * score版接口(微软)
     */
    @PostMapping("/score")
    public ScoreEvaluationResponse scoreEvaluate(@RequestBody ScoreEvaluationRequest req) throws Exception {
        ScoreEvaluationResponse response = evaluateService.evaluateScore(req);
        if (response == null)
            throw new Exception("调用失败，请重试");
        return response;
    }
}
