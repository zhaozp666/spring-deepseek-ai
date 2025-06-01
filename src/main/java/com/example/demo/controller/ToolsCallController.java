package com.example.demo.controller;

import com.example.demo.tools.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ToolsCallController {
    private final OllamaChatModel chatModel;

    public ToolsCallController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/toolChat")
    public String generate() {
        ChatClient chatClient = ChatClient.create(chatModel);
        return chatClient.prompt("今天是几号")
                .tools(new DateTimeTools()).call().content();
    }
}

