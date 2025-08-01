package com.xhpolaris.essay_stateless.essay.util;

import com.xhpolaris.essay_stateless.essay.core.beta.api.APIWordSentence;
import com.xhpolaris.essay_stateless.utils.CozeHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class CozeAdaptor {
    @Async
    public CompletableFuture<APIWordSentence> wordSentenceAsync(CozeHelper helper, Map<String, Object> essay) {
        try {
            APIWordSentence result = WordSentence(helper, essay);
            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            // 异常时返回失败的Future
            return CompletableFuture.failedFuture(e);
        }
    }

    public APIWordSentence WordSentence(CozeHelper helper, Map<String, Object> essay) throws Exception {
        // 调用Coze API获取结果
        helper.createAndPoll(formatEssayToMsg(essay));
        Map<String, Object> cozeJson = helper.getJSON();

        // 创建APIWordSentence对象并设置基本评分信息
        APIWordSentence wordSentence = new APIWordSentence();
        wordSentence.score = cozeJson.containsKey("grade") ? (Integer) cozeJson.get("grade") : null;
        wordSentence.comment = cozeJson.containsKey("comment_gsgw") ? (String) cozeJson.get("comment_gsgw") : null;

        // 创建WordSentenceData结构
        APIWordSentence.WordSentenceData data = new APIWordSentence.WordSentenceData();
        APIWordSentence.WordSentenceResult results = new APIWordSentence.WordSentenceResult();
        data.setResults(results);
        wordSentence.setData(data);

        // 处理好句列表
        if (cozeJson.containsKey("good_sents")) {
            List<Map<String, Object>> goodSentList = (List<Map<String, Object>>) cozeJson.get("good_sents");
            List<APIWordSentence.GoodSentenceResult> goodSents = new ArrayList<>();

            for (Map<String, Object> sentMap : goodSentList) {
                APIWordSentence.GoodSentenceResult sentResult = new APIWordSentence.GoodSentenceResult();
                sentResult.setParagraphId(safeGetInteger(sentMap, "paragraph_id"));
                sentResult.setSentId(safeGetInteger(sentMap, "sent_id"));
                sentResult.setLabel(safeGetString(sentMap, "label"));
                sentResult.setType(safeGetString(sentMap, "type"));
                sentResult.setComment(safeGetString(sentMap, "comment"));
                sentResult.setScore(safeGetInteger(sentMap, "score"));

                goodSents.add(sentResult);
            }
            results.setGoodSents(goodSents);
        }

        // 处理好词列表
        if (cozeJson.containsKey("good_words")) {
            List<Map<String, Object>> goodWordList = (List<Map<String, Object>>) cozeJson.get("good_words");
            List<APIWordSentence.GoodWordResult> goodWords = new ArrayList<>();

            for (Map<String, Object> wordMap : goodWordList) {
                APIWordSentence.GoodWordResult wordResult = new APIWordSentence.GoodWordResult();
                wordResult.setParagraphId(safeGetInteger(wordMap, "paragraph_id"));
                wordResult.setSentId(safeGetInteger(wordMap, "sent_id"));
                wordResult.setStart(safeGetInteger(wordMap, "start"));
                wordResult.setEnd(safeGetInteger(wordMap, "end"));
                wordResult.setComment(safeGetString(wordMap, "comment"));

                goodWords.add(wordResult);
            }
            results.setGoodWords(goodWords);
        }

        return wordSentence;
    }

    // 安全获取Integer类型值
    private Integer safeGetInteger(Map<String, Object> map, String key) {
        if (!map.containsKey(key)) return null;
        Object value = map.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return null;
    }

    // 安全获取String类型值
    private String safeGetString(Map<String, Object> map, String key) {
        if (!map.containsKey(key)) return null;
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    public static String formatEssayToMsg(Map<String, Object> essay) {
        if (essay == null || essay.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("基本信息:\n")
                .append("作文类型").append(essay.get("essay_type"))
                .append("作者年级").append(essay.get("grade_int"))
                .append("\n")
                .append("作文标题:").append(essay.get("title"))
                .append("作文内容(以换行符分割段落):").append(essay.get("essay"));
        return sb.toString();
    }
}
