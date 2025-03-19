package com.xhpolaris.essay_stateless.essay.aspect;

import com.xhpolaris.essay_stateless.essay.annotation.RawLog;
import com.xhpolaris.essay_stateless.essay.entity.RawLogs;
import com.xhpolaris.essay_stateless.essay.repo.RawLogsRepository;
import com.xhpolaris.essay_stateless.essay.req.EvaluateRequest;
import com.xhpolaris.essay_stateless.essay.resp.EvaluateResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * AOP日志
 * 拦截所有@RawLog注解的方法，将uri、req、resp、create_time记录到数据库中
 * req 和 resp 分别需要实现EvaluateReq和EvaluateResp
 */

@Aspect
@Component
@RequiredArgsConstructor
public class RowLogAspect {
    private final RawLogsRepository rawLogsRepository;

    /**
     * 定义切点：拦截所有标记了@RawLog的方法
     */
    @Pointcut("@annotation(rawLog)")
    public void rawLogMethods(RawLog rawLog) {
    }

    /**
     * 在方法执行后记录日志
     */
    @AfterReturning(pointcut = "rawLogMethods(rawLog)", returning = "response", argNames = "joinPoint,response,rawLog")
    public void rawLog(JoinPoint joinPoint, EvaluateResponse response, RawLog rawLog) {
        // 直接从注解中获取URI
        String uri = rawLog.value();

        // 获取请求参数（假设第一个参数是请求体）
        EvaluateRequest request = (EvaluateRequest) joinPoint.getArgs()[0];

        // 使用JSON序列化
        String reqJson = request.jsonString();
        String respJson = response.jsonString();

        // 保存日志到数据库
        saveLogs(uri, reqJson, respJson);
    }

    /**
     * 保存日志到数据库
     */
    private void saveLogs(String uri, String request, String response) {
        RawLogs log = new RawLogs();
        log.setUrl(uri);
        log.setRequest(request);
        log.setResponse(response);
        log.setCreateTime(LocalDateTime.now());
        rawLogsRepository.save(log);
    }
}
