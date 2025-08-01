package com.xhpolaris.essay_stateless.utils;

import java.util.Collections;
import java.util.Map;

import com.coze.openapi.client.chat.*;
import com.coze.openapi.client.chat.message.ListMessageReq;
import com.coze.openapi.client.chat.message.ListMessageResp;
import com.coze.openapi.client.chat.model.ChatPoll;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CozeHelper {

    private final String bot;
    private final String user;
    private final CozeAPI coze;
    private final int timeout;
    private String chatID;
    private String conversationID;

    public CozeHelper(String base, String token, String bot, String user, int timeout) {
        this.bot = bot;
        this.user = user;
        this.timeout = timeout;
        TokenAuth authCli = new TokenAuth(token);
        this.coze = new CozeAPI.Builder().baseURL(base)
                .auth(authCli).readTimeout(timeout).build();
    }

    public void createAndPoll(String msg) throws Exception {
        CreateChatReq req =
                CreateChatReq.builder()
                        .botID(this.bot)
                        .userID(this.user)
                        .messages(Collections.singletonList(Message.buildUserQuestionText(msg)))
                        .build();
        ChatPoll chat = coze.chat().createAndPoll(req, (long) timeout);
        this.conversationID = chat.getChat().getConversationID();
        this.chatID = chat.getChat().getID();
    }

    public Map<String, Object> getJSON() {
        ListMessageResp resp = coze.chat().message().list(ListMessageReq.builder().chatID(chatID).
                conversationID(conversationID).build());
        String result = removeMarkdown(resp.getMessages().get(0).getContent());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(result, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON", e);
        }
    }

    private String removeMarkdown(String markdown) {
        if (markdown == null || markdown.isEmpty()) {
            return markdown;
        }
        // 替换代码块标记但保留内容
        String result = markdown.replaceAll("```", "");
        return result.trim();
    }
}
