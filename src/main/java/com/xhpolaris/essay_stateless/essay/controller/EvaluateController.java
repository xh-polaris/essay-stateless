package com.xhpolaris.essay_stateless.essay.controller;

import com.xhpolaris.essay_stateless.annotation.RawLog;
import com.xhpolaris.essay_stateless.essay.entity.evaluation.BetaEvaluateResponse;
import com.xhpolaris.essay_stateless.essay.entity.logs.RawLogs;
import com.xhpolaris.essay_stateless.essay.req.BetaOcrEvaluateRequest;
import com.xhpolaris.essay_stateless.essay.req.BetaEvaluateRequest;
import com.xhpolaris.essay_stateless.essay.req.ScoreEvaluateRequest;
import com.xhpolaris.essay_stateless.essay.entity.scoreEvaluation.ScoreEvaluateResponse;
import com.xhpolaris.essay_stateless.essay.repo.RawLogsRepository;
import com.xhpolaris.essay_stateless.essay.core.EvaluateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/evaluate")
@AllArgsConstructor
public class EvaluateController {

    private final EvaluateService evaluateService;

    /**
     * beta版批改接口
     */
    @PostMapping
    @RawLog("/evaluate")
    public BetaEvaluateResponse betaEvaluate(@RequestBody BetaEvaluateRequest req) throws Exception {
        return evaluateService.evaluate(req.title, req.content, req.grade);
    }

    /**
     * beta版批改 - ocr
     */
    @PostMapping("/beta/ocr")
    @RawLog("/evaluate/beta/ocr")
    public BetaEvaluateResponse betaOcrEvaluate(@RequestBody BetaOcrEvaluateRequest req) throws Exception {
        return evaluateService.betaOcrEvaluate(req.getImages(), req.getGrade());
    }

    /**
     * score版接口
     */
    @PostMapping("/score")
    @RawLog("/evaluate/score")
    public ScoreEvaluateResponse scoreEvaluate(@RequestBody ScoreEvaluateRequest req) throws Exception {
        return evaluateService.evaluateScore(req);
    }
}
