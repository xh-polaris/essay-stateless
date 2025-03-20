package com.xhpolaris.essay_stateless.essay.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * beta批改接口用到的工具类
 */
@Slf4j
public class BetaHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 段 -句 - 相对索引(句内)
     */
    public static class GrammarPosition {
        public int paragraphIndex; // 段落索引
        public int sentenceIndex;  // 句子索引
        public int relativeIndex;  // 相对索引

        public GrammarPosition(int paragraphIndex, int sentenceIndex, int relativeIndex) {
            this.paragraphIndex = paragraphIndex;
            this.sentenceIndex = sentenceIndex;
            this.relativeIndex = relativeIndex;
        }
    }

    /**
     * 将全文索引转换为 段-句相对索引
     *
     * @param text  段-句划分
     * @param index 全文索引
     * @return 相对索引
     */
    public static GrammarPosition getSentenceRelativeIndex(List<List<String>> text, int index) {
        int currentPosition = 0; // 全文中当前字符的偏移量
        int paragraphIndex = 0;  // 当前段落索引

        for (List<String> paragraph : text) {
            int sentenceIndex = 0; // 当前句子索引
            for (String sentence : paragraph) {
                int sentenceStart = currentPosition; // 句子的起始位置
                int sentenceEnd = currentPosition + sentence.length(); // 句子的结束位置

                // 下游算法计算索引时考虑了\n的位置
                // text中是不存在\n的
                if (sentenceStart <= index - paragraphIndex && index - paragraphIndex < sentenceEnd) {
                    int relativeIndex = index - paragraphIndex; // 语法错误出现的位置相对于所遍历的该句子的偏移量
                    return new GrammarPosition(paragraphIndex, sentenceIndex, relativeIndex);
                }

                currentPosition = sentenceEnd; // 更新当前位置
                sentenceIndex++; // 句子索引加 1
            }
            paragraphIndex++; // 段落索引加 1
        }

        return null; // 如果索引不在任何句子中
    }

    public static String jsonString(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            log.error("{} json化失败，err: {}", o, e.getMessage());
            return "";
        }
    }
}
