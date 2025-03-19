package com.xhpolaris.essay_stateless.essay.core.beta;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhpolaris.essay_stateless.essay.config.ModelVersion;
import com.xhpolaris.essay_stateless.essay.core.beta.api.*;
import com.xhpolaris.essay_stateless.essay.core.beta.fields.*;
import com.xhpolaris.essay_stateless.essay.resp.EvaluateResponse;
import com.xhpolaris.essay_stateless.essay.util.BetaHelper;
import lombok.Data;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Beta版接口批改响应
 * fields:
 * - title: 作文标题
 * - text: 作文段-句划分
 * - aiEvaluation: 算法批改结果
 */
@Data
public class BetaEvaluateResponse implements EvaluateResponse {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String title;
    private List<List<String>> text;
    private EssayInfo essayInfo;
    private AIEvaluation aiEvaluation;

    /**
     * 初始化批改结果
     *
     * @param mv 模型信息
     */
    public BetaEvaluateResponse(ModelVersion mv) {
        text = new ArrayList<>();
        aiEvaluation = new AIEvaluation(mv);
    }

    public void process(CompletableFuture<APIOverall> overall, CompletableFuture<APIFluency> fluency,
                        CompletableFuture<APIWordSentence> wordSentence, CompletableFuture<APIGrammarInfo> grammarInfo,
                        CompletableFuture<APIExpression> expression, CompletableFuture<APISuggestion> suggestion,
                        CompletableFuture<APIParagraph> paragraph) {
        this.processWordSentence(get(wordSentence));
        this.processGrammarInfo(get(grammarInfo));
        this.processFluency(get(fluency));
        this.processParagraph(get(paragraph));
        this.processExpression(get(expression));
        this.processOverall(get(overall));
        this.processSuggestion(get(suggestion));
    }

    /**
     * 处理作文信息
     *
     * @param title 作文标题
     * @param info  作文信息
     */
    public void processEssayInfo(String title, APIEssayInfo info) {
        // 标题
        this.title = title;
        // 段-句划分
        this.text = info.sents;
        // 作文基本类型
        this.essayInfo.essayType = info.essayType;
        // 作文年级
        this.essayInfo.grade = info.grade;
        // 统计信息
        APIEssayInfo.Counting c = info.counting;
        this.essayInfo.counting = new EssayInfo.Counting(c.adjAdvNum, c.charNum, c.dieciNum, c.fluency, c.grammarMistakeNum, c.highlightSentsNum, c.idiomNum, c.nounTypeNum, c.paraNum, c.sentNum, c.uniqueWordNum, c.verbTypeNum, c.wordNum, c.writtenMistakeNum);
    }

    /**
     * 处理总评
     *
     * @param response 总评响应
     */
    private void processOverall(APIOverall response) {
        if (response == null)
            return;
        this.aiEvaluation.overallEvaluation.description = response.comment;
        this.aiEvaluation.overallEvaluation.topicRelevanceScore = response.score;
    }

    /**
     * 处理流畅度评价
     *
     * @param response 流畅度响应
     */
    private void processFluency(APIFluency response) {
        if (response == null)
            return;
        this.aiEvaluation.fluencyEvaluation.fluencyDescription = response.getComment();
        this.aiEvaluation.fluencyEvaluation.fluencyScore = response.getScore();
    }

    /**
     * 好词好句处理
     *
     * @param response 好词好句响应
     */
    private void processWordSentence(APIWordSentence response) {
        if (response == null)
            return;

        APIWordSentence.WordSentenceData wordSentenceData = response.getData();

        // 根据段-句划分初始化每一句的评价
        List<List<WordSentenceEvaluation.SentenceEvaluation>> sentencesEvaluations = new ArrayList<>();
        for (List<String> strings : text) {
            List<WordSentenceEvaluation.SentenceEvaluation> tmp = new ArrayList<>();
            for (int j = 0; j < strings.size(); ++j) {
                tmp.add(new WordSentenceEvaluation.SentenceEvaluation());
            }
            sentencesEvaluations.add(tmp);
        }

        // 设置好句
        wordSentenceData.results.goodSents.forEach(sent -> {
            // 根据段-句划分获取对应句子评价
            WordSentenceEvaluation.SentenceEvaluation sentenceEvaluation = sentencesEvaluations.get(sent.paragraphId).get(sent.sentId);
            // 设置好句信息
            sentenceEvaluation.isGoodSentence = true;
            sentenceEvaluation.label = sent.getLabel();
            Map<String, String> type = new HashMap<>();
            type.put("level1", "作文亮点");
            type.put("level2", "好句");
            sentenceEvaluation.type = type;
        });

        wordSentenceData.results.goodWords.forEach(word -> {
            // 根据段-句划分获取对应句子评价
            WordSentenceEvaluation.WordEvaluation wordEvaluation = new WordSentenceEvaluation.WordEvaluation();
            // 设置好词信息
            wordEvaluation.span.add(word.start);
            wordEvaluation.span.add(word.end);
            wordEvaluation.type.put("level1", "作文亮点");
            wordEvaluation.type.put("level2", "好词");
            sentencesEvaluations.get(word.getParagraphId()).get(word.getSentId()).wordEvaluations.add(wordEvaluation);
        });

        this.aiEvaluation.wordSentenceEvaluation.sentenceEvaluations = sentencesEvaluations;
        this.aiEvaluation.wordSentenceEvaluation.wordSentenceDescription = response.comment;
        this.aiEvaluation.wordSentenceEvaluation.wordSentenceScore = response.score;
    }

    /**
     * 处理语法错误
     *
     * @param response 语法错误响应
     */
    private void processGrammarInfo(APIGrammarInfo response) {
        if (response == null)
            return;

        List<APIGrammarInfo.Typo> typos = response.grammar.typo;
        typos.forEach(typo -> {
            // 获取全文中的索引
            int startPos = typo.getStartPos();
            int endPos = typo.getEndPos();

            // 计算句子中相对索引
            BetaHelper.GrammarPosition gp = BetaHelper.getSentenceRelativeIndex(this.text, startPos);
            // 为空则不计算这一错误
            if (gp == null)
                return;

            WordSentenceEvaluation.WordEvaluation wordEvaluation = new WordSentenceEvaluation.WordEvaluation();

            // 赋值数据
            wordEvaluation.span.add(gp.relativeIndex);
            wordEvaluation.span.add(gp.relativeIndex + endPos - startPos);
            wordEvaluation.type.put("level1", "还需努力");
            wordEvaluation.type.put("level2", typo.getType());
            wordEvaluation.ori = typo.getOri();
            wordEvaluation.revised = typo.getRevised();

            // 向对应句子中添加对应错误
            this.aiEvaluation.wordSentenceEvaluation.sentenceEvaluations.get(gp.paragraphIndex)
                    .get(gp.sentenceIndex).getWordEvaluations().add(wordEvaluation);
        });
    }

    /**
     * 处理表达
     *
     * @param response 表达响应
     */
    private void processExpression(APIExpression response) {
        if (response == null)
            return;
        // 表达评价
        this.aiEvaluation.expressionEvaluation.expressDescription = response.comment;
        // 表达分数
        this.aiEvaluation.expressionEvaluation.expressionScore = response.score;
    }

    /**
     * 处理修改建议
     *
     * @param response 修改建议响应
     */
    private void processSuggestion(APISuggestion response) {
        if (response == null)
            return;
        this.aiEvaluation.suggestionEvaluation.suggestionDescription = response.comment;
    }

    /**
     * 处理段落点评
     *
     * @param response 段落批改响应
     */
    private void processParagraph(APIParagraph response) {
        if (response == null)
            return;
        // 按序遍历每一条段落, 添加到段落点评中
        for (int i = 0; i < response.comments.size(); ++i) {
            this.aiEvaluation.paragraphEvaluations.add(
                    new ParagraphEvaluation(i, response.comments.get(i))
            );
        }
    }


    // 从CompleteFuture中获取对象，异常时返回空
    private <T> T get(CompletableFuture<T> response) {
        try {
            return response.get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String jsonString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            return "序列化失败";
        }
    }
}
