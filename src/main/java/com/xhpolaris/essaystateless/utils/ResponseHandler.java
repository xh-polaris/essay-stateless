package com.xhpolaris.essaystateless.utils;

import java.util.List;

public class ResponseHandler {

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

    public static GrammarPosition getSentenceRelativeIndex(List<List<String>> text, int index) {
        int currentPosition = 0; // 全文中当前字符的偏移量
        int paragraphIndex = 0;  // 当前段落索引

        for (List<String> paragraph : text) {
            int sentenceIndex = 0; // 当前句子索引
            for (String sentence : paragraph) {
                int sentenceStart = currentPosition; // 句子的起始位置
                int sentenceEnd = currentPosition + sentence.length(); // 句子的结束位置

                if (sentenceStart <= index - paragraphIndex && index - paragraphIndex < sentenceEnd) {
                    int relativeIndex = index - paragraphIndex - sentenceStart; // 语法错误出现的位置相对于所遍历的该句子的偏移量
                    //relativeIndex -= paragraphIndex;  // 修正下游算法不考虑/n带来的误差
                    return new GrammarPosition(paragraphIndex, sentenceIndex, relativeIndex);
                }

                currentPosition = sentenceEnd; // 更新当前位置
                sentenceIndex++; // 句子索引加 1
            }
            paragraphIndex++; // 段落索引加 1
        }

        return null; // 如果索引不在任何句子中
    }
}
