package com.xhpolaris.essaystateless.entity.evaluation;

import com.xhpolaris.essaystateless.entity.evaluation.api.*;
import com.xhpolaris.essaystateless.entity.evaluation.fields.ExpressionEvaluation;
import com.xhpolaris.essaystateless.entity.evaluation.fields.ModelVersion;
import com.xhpolaris.essaystateless.entity.evaluation.fields.ParagraphEvaluation;
import com.xhpolaris.essaystateless.entity.evaluation.fields.WordSentenceEvaluation;
import com.xhpolaris.essaystateless.utils.ResponseHandler;
import lombok.Data;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.xhpolaris.essaystateless.utils.ResponseHandler.getSentenceRelativeIndex;

@Data
public class EvaluationResponse {
    public String title;
    public List<List<String>> text;
    public AIEvaluation aiEvaluation;

    public EvaluationResponse(ModelVersion mv) {
        text = new ArrayList<>();
        aiEvaluation = new AIEvaluation(mv);
    }

    //    public void process(CompletableFuture<APICommonResponse> overall, CompletableFuture<APIFluencyResponse> fluency,
//                        CompletableFuture<APIWordSentenceResponse> wordSentence, CompletableFuture<APIBadWordResponse> badWords,
//                        CompletableFuture<APIExpressionResponse> expression, CompletableFuture<APICommonResponse> suggestion,
//                        CompletableFuture<APICommonResponse> paragraph) {
//        this.processWordSentence(get(wordSentence));
//        this.processBadWords(get(badWords));
//        this.processFluency(get(fluency));
//        this.processParagraph(get(paragraph));
//        this.processExpression(get(expression));
//        this.processOverall(get(overall));
//        this.processSuggestion(get(suggestion));
//    }
    public void process(CompletableFuture<APICommonResponse> overall, CompletableFuture<APIFluencyResponse> fluency,
                        CompletableFuture<APIWordSentenceResponse> wordSentence, CompletableFuture<APIGrammarInfoResponse> grammarInfo,
                        CompletableFuture<APIExpressionResponse> expression, CompletableFuture<APICommonResponse> suggestion,
                        CompletableFuture<APICommonResponse> paragraph) {
        this.processWordSentence(get(wordSentence));
        this.processGrammarInfo(get(grammarInfo));
        this.processFluency(get(fluency));
        this.processParagraph(get(paragraph));
        this.processExpression(get(expression));
        this.processOverall(get(overall));
        this.processSuggestion(get(suggestion));
    }

    private void processOverall(APICommonResponse response) {
        if (response == null)
            return;
        this.aiEvaluation.overallEvaluation.description = response.getComment();
        this.aiEvaluation.overallEvaluation.topicRelevanceScore = response.getScore();
    }

    private void processFluency(APIFluencyResponse response) {
        if (response == null)
            return;
        List<APIFluencyResponse.SickSentence> sickSentences = response.getData().getResults().getSickSentences();
        List<String> level2Wrong = new ArrayList<>(Arrays.asList(
                "错别字错误", "缺字漏字", "缺少标点", "错用标点", "成分残缺型错误", "成分赘余型错误", "成分搭配不当型错误", "不合逻辑"
        ));
        for (APIFluencyResponse.SickSentence sickSentence : sickSentences) {
            if (this.aiEvaluation.wordSentenceEvaluation.sentenceEvaluations
                    .get(sickSentence.getParagraphId()).get(sickSentence.getSentenceId()).getIsGoodSentence()
                    != null) {
                continue;
            }
            Map<String, String> type = new HashMap<>();
            type.put("level1", "还需努力");
            String label = sickSentence.getLabel().split("，")[0];
            if (level2Wrong.contains(label)) {
                type.put("level2", label);
            } else {
                switch (label) {
                    case "主语不明":
                    case "谓语残缺":
                    case "宾语残缺":
                    case "其他成分残缺":
                        type.put("level2", "成分残缺型错误");
                        break;
                    case "主语多余":
                    case "虚词多余":
                    case "语句重复":
                    case "其他成分多余":
                        type.put("level2", "成分赘余型错误");
                        break;
                    case "主谓搭配不当":
                    case "动宾搭配不当":
                    case "语序不当":
                    case "其他搭配不当":
                        type.put("level2", "成分搭配不当型错误");
                        break;
                    case "不合语言逻辑":
                    case "不合事实逻辑":
                        type.put("level2", "不合逻辑");
                        break;
                    default:
                        type.put("level2", "其他错误");
                }
                type.put("level3", label);
            }
            this.aiEvaluation.wordSentenceEvaluation.sentenceEvaluations.get(sickSentence.getParagraphId())
                    .get(sickSentence.getSentenceId()).isGoodSentence = true;
            this.aiEvaluation.wordSentenceEvaluation.sentenceEvaluations.get(sickSentence.getParagraphId())
                    .get(sickSentence.getSentenceId()).type = type;
        }
        this.aiEvaluation.fluencyEvaluation.fluencyDescription = response.getComment();
        this.aiEvaluation.fluencyEvaluation.fluencyScore = response.getScore();
    }

    private void processWordSentence(APIWordSentenceResponse response) {
        if (response == null)
            return;
        APIWordSentenceResponse.WordSentenceData wordSentenceData = response.getData();

        this.text = wordSentenceData.getSentences();
        List<List<WordSentenceEvaluation.SentenceEvaluation>> sentencesEvaluations = new ArrayList<>();
        for (int i = 0; i < wordSentenceData.getSentences().size(); ++i) {
            List<WordSentenceEvaluation.SentenceEvaluation> tmp = new ArrayList<>();
            for (int j = 0; j < wordSentenceData.getSentences().get(i).size(); ++j) {
                tmp.add(new WordSentenceEvaluation.SentenceEvaluation());
            }
            sentencesEvaluations.add(tmp);
        }
        wordSentenceData.getResults().getGoodSents().forEach(sent -> {
            WordSentenceEvaluation.SentenceEvaluation sentenceEvaluation = sentencesEvaluations.get(sent.getParagraphId()).get(sent.getSentId());
            sentenceEvaluation.isGoodSentence = true;
            sentenceEvaluation.label = sent.getLabel();
            Map<String, String> type = new HashMap<>();
            type.put("level1", "作文亮点");
            sentenceEvaluation.type = type;
        });
        wordSentenceData.getResults().getGoodWords().forEach(word -> {
            WordSentenceEvaluation.WordEvaluation wordEvaluation = new WordSentenceEvaluation.WordEvaluation();
            wordEvaluation.span.add(word.getStart());
            wordEvaluation.span.add(word.getEnd());
            wordEvaluation.type.put("level1", "作文亮点");
            wordEvaluation.type.put("level2", "其他");
            sentencesEvaluations.get(word.getParagraphId()).get(word.getSentId()).wordEvaluations.add(wordEvaluation);
        });
        this.aiEvaluation.wordSentenceEvaluation.sentenceEvaluations = sentencesEvaluations;
        this.aiEvaluation.wordSentenceEvaluation.wordSentenceDescription = response.getComment();
        this.aiEvaluation.wordSentenceEvaluation.wordSentenceScore = response.getScore();
    }

    private void processBadWords(APIBadWordResponse response) {
        if (response == null)
            return;
        APIBadWordResponse.BadWordRecognizeResult badWordRecognizeResult = response.getData().getResults();

        badWordRecognizeResult.getCgec().forEach(result -> {
            WordSentenceEvaluation.WordEvaluation wordEvaluation = new WordSentenceEvaluation.WordEvaluation();
            wordEvaluation.span.add(result.getTokenIdx().get(0));
            wordEvaluation.span.add(result.getTokenIdx().get(0) + 1);
            wordEvaluation.type.put("level1", "还需努力");
            wordEvaluation.type.put("level2", "错别字错误");
            this.aiEvaluation.wordSentenceEvaluation.sentenceEvaluations.get(result.getParagraphId())
                    .get(result.getSendId()).getWordEvaluations().add(wordEvaluation);
        });
    }

    private void processGrammarInfo(APIGrammarInfoResponse response) {
        if (response == null)
            return;
        APIGrammarInfoResponse.GrammarInfo grammarInfo = response.getGrammar();

        grammarInfo.getTypo().forEach(result -> {
            // 获取全文中的索引
            int endPos = result.getEndPos();
            int startPos = result.getStartPos();
            // 计算句子中相对索引
            ResponseHandler.GrammarPosition gp = ResponseHandler.getSentenceRelativeIndex(this.text, startPos);
            if (gp == null) {
                return;
            }
            WordSentenceEvaluation.WordEvaluation wordEvaluation = new WordSentenceEvaluation.WordEvaluation();
            wordEvaluation.span.add(gp.relativeIndex);
            wordEvaluation.span.add(gp.relativeIndex + endPos - startPos);
            wordEvaluation.type.put("level1", "还需努力");
            wordEvaluation.type.put("level2", result.getType());
            wordEvaluation.ori = result.getOri();
            wordEvaluation.revised = result.getRevised();
            this.aiEvaluation.wordSentenceEvaluation.sentenceEvaluations.get(gp.paragraphIndex)
                    .get(gp.sentenceIndex).getWordEvaluations().add(wordEvaluation);
        });
    }

    private void processExpression(APIExpressionResponse response) {
        if (response == null)
            return;

        for (int i = 0; i < this.aiEvaluation.paragraphEvaluations.size(); ++i) {
            this.aiEvaluation.expressionEvaluation.expressionEvaluations.add(new ArrayList<>());
        }
        for (APIExpressionResponse.SentenceRelation sentenceRelation : response.getData().getResults().getSentenceRelations()) {
            this.aiEvaluation.expressionEvaluation.expressionEvaluations.get(sentenceRelation.getParagraphId()).add(
                    new ExpressionEvaluation.SubExpressionEvaluation(sentenceRelation.getStart(), sentenceRelation.getEnd(), sentenceRelation.getLabel())
            );
        }
        this.aiEvaluation.expressionEvaluation.expressDescription = response.getComment();
        this.aiEvaluation.expressionEvaluation.expressionScore = response.getScore();
    }

    private void processSuggestion(APICommonResponse response) {
        if (response == null)
            return;
        this.aiEvaluation.suggestionEvaluation.suggestionDescription = response.getComment();
    }

    private void processParagraph(APICommonResponse response) {
        if (response == null)
            return;
        for (int i = 0; i < response.getComments().size(); ++i) {
            ParagraphEvaluation paragraphEvaluation = new ParagraphEvaluation();
            paragraphEvaluation.paragraphIndex = i;
            paragraphEvaluation.content = response.getComments().get(i);
            this.aiEvaluation.paragraphEvaluations.add(paragraphEvaluation);
        }
    }


    private <T> T get(CompletableFuture<T> response) {
        try {
            return response.get();
        } catch (Exception e) {
            return null;
        }
    }
}
