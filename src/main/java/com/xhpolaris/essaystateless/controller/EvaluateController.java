package com.xhpolaris.essaystateless.controller;

import com.xhpolaris.essaystateless.entity.evaluation.EvaluationResponse;
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

    @PostMapping
    public EvaluationResponse evaluate(@RequestBody EvaluateRequest req) throws Exception {
        EvaluationResponse response = evaluateService.evaluate(req.title, req.content);
        if (response == null) {
            throw new Exception("评估失败，请重试");
        }
        return response;
    }

    @PostMapping("/score")
    public ScoreEvaluationResponse azureEvaluate(@RequestBody ScoreEvaluationRequest req) throws Exception {
        ScoreEvaluationResponse response = evaluateService.evaluateScore(req);
        if (response == null)
            throw new Exception("调用失败，请重试");
        return response;
    }

}
