package com.xhpolaris.essaystateless.service;

import com.xhpolaris.essaystateless.entity.enEvaluation.APIEnErrorResponse;
import com.xhpolaris.essaystateless.entity.request.ModuleRequest;
import com.xhpolaris.essaystateless.utils.HttpClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class EnEvaluateService {
    private final HttpClient httpClient;

    public APIEnErrorResponse enGrammarModuleEvaluate(ModuleRequest req) {
        Map<String, Object> essay = new HashMap<>();
        if (req.title != null)
            essay.put("title", req.getTitle());
        essay.put("essay", req.getEssay());
        return httpClient.syncCall("Grammatical_Error_Correction", APIEnErrorResponse.class, essay);
    }

}

