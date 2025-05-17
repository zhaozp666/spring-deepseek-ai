package com.example.demo.util;

import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;

public class CommonUtil {

    public static Flux<String> extractTextFromChatResponse(Flux<ChatResponse> chatResponseFlux) {
        return chatResponseFlux
                .map(chatResponse -> {
                    // 从 ChatResponse 中提取文本
                    return chatResponse.getResults().stream()
                            .map(result -> result.getOutput().getText())
                            .collect(Collectors.joining());
                });
    }

}
