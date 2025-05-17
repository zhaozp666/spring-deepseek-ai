package com.example.demo.controller;

import com.example.demo.util.CommonUtil;
import com.example.demo.vo.BasicVo;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;


@RestController
@RequestMapping("/api/bigModel")
public class ChatController {

    private final OllamaChatModel chatModel;

    public ChatController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }


    @GetMapping("/chat")
    public BasicVo<String> generate(@RequestParam(value = "message") String message) {
        return BasicVo.success(this.chatModel.call(message));
    }

    @GetMapping("/chatStream")
    public Flux<String> generateStream(@RequestParam(value = "message") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        Flux<ChatResponse> responseFlux = this.chatModel.stream(prompt)
                .timeout(Duration.ofSeconds(100))
                .retry(3);
        Flux<String> textFlux = CommonUtil.extractTextFromChatResponse(responseFlux);
        return textFlux;
    }


}

