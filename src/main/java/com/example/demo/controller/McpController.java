package com.example.demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class McpController {
    private final OllamaChatModel chatModel;
    private final SyncMcpToolCallbackProvider syncMcpToolCallbackProvider;

    public McpController(OllamaChatModel chatModel,SyncMcpToolCallbackProvider syncMcpToolCallbackProvider) {
        this.chatModel = chatModel;
        this.syncMcpToolCallbackProvider = syncMcpToolCallbackProvider;
    }

    @GetMapping("/mcpChat")
    public String generate(@RequestParam(value = "message") String message) {
        ChatClient chatClient = ChatClient.builder(chatModel)
                .defaultTools(syncMcpToolCallbackProvider.getToolCallbacks())
                .build();
        return chatClient.prompt().user(message).call().content();
    }
}

