package com.xhpolaris.essaystateless.controller;

import com.xhpolaris.essaystateless.entity.evaluation.EvaluationResponse;
import com.xhpolaris.essaystateless.entity.logs.RawLogs;
import com.xhpolaris.essaystateless.entity.request.BetaOcrEvaluateRequest;
import com.xhpolaris.essaystateless.entity.request.EvaluateRequest;
import com.xhpolaris.essaystateless.entity.request.ScoreEvaluationRequest;
import com.xhpolaris.essaystateless.entity.result.ResponseResult;
import com.xhpolaris.essaystateless.exception.ExceptionCode;
import com.xhpolaris.essaystateless.entity.scoreEvaluation.ScoreEvaluationResponse;
import com.xhpolaris.essaystateless.exception.BizException;
import com.xhpolaris.essaystateless.repository.RawLogsRepository;
import com.xhpolaris.essaystateless.service.EvaluateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/evaluate")
@AllArgsConstructor
public class EvaluateController {

    private final RawLogsRepository rawLogsRepository;
    private final EvaluateService evaluateService;

    /*
     * beta版批改(学校)
     */
    @PostMapping
    public ResponseResult<EvaluationResponse> evaluate(@RequestBody EvaluateRequest req) throws Exception {
        EvaluationResponse response = evaluateService.evaluate(req.title, req.content, req.grade);
        if (response == null) {
            throw new BizException(ExceptionCode.EVALUATE_GRADE_ERROR);
        }
        saveLogs("/evaluate/", req, response);
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }

    /*
     * beta版ocr批改
     */
    @PostMapping("/beta/ocr")
    public ResponseResult<EvaluationResponse> betaOcrEvaluate(@RequestBody BetaOcrEvaluateRequest req) throws Exception {
        EvaluationResponse response = evaluateService.betaOcrEvaluate(req.getImages(), req.getGrade());
        if (response == null) {
            throw new BizException(ExceptionCode.EVALUATE_GRADE_ERROR);
        }
        saveLogs("/evaluate/beta/ocr", req, response);
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }

    /*
     * score版接口(微软)
     */
    @PostMapping("/score")
    public ResponseResult<ScoreEvaluationResponse> scoreEvaluate(@RequestBody ScoreEvaluationRequest req) throws Exception {
        ScoreEvaluationResponse response = evaluateService.evaluateScore(req);
        if (response == null)
            throw new BizException(ExceptionCode.EVALUATE_GRADE_ERROR);
        saveLogs("/evaluate/score", req, response);
        return new ResponseResult<>(ExceptionCode.SUCCESS, response);
    }

    private void saveLogs(String uri, Object req, Object resp) {
        // 存储请求信息
        RawLogs log = new RawLogs();
        log.setUrl(uri);
        log.setRequest(req.toString());
        log.setResponse(resp.toString());
        log.setCreateTime(LocalDateTime.now());  // 用秒级时间戳

        rawLogsRepository.save(log);
    }
}
