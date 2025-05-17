package com.example.demo.controller;

import com.example.demo.service.DocumentService;
import com.example.demo.service.StoreService;
import com.example.demo.vo.BasicVo;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever.builder;


@RestController
public class RagController {

    private final OllamaChatModel chatModel;
    private final DocumentService documentService;
    private final StoreService storeService;

    public RagController(OllamaChatModel chatModel, DocumentService documentService, StoreService storeService) {
        this.chatModel = chatModel;
        this.documentService = documentService;
        this.storeService = storeService;
    }

    @GetMapping("/doc")
    public List<Document> doc() {
        return documentService.loadDocuments();
    }

    @GetMapping("/store")
    public String store() {
        List<Document> documentList = documentService.loadDocuments();
        storeService.store(documentList);
        return "document save ok";
    }

    @GetMapping("/query")
    public List<Document> query(@RequestParam(value = "message") String message) {
        SearchRequest request = SearchRequest.builder()
                .query(message)
                .topK(3)
                .similarityThreshold(0.5)
                .build();
        return storeService.search(request);
    }

    @GetMapping("/ragChat")
    public BasicVo<String> ragChat(@RequestParam(value = "message") String message) {
        //基于RAG的结果构造prompt的上下文
        SearchRequest request = SearchRequest.builder()
                .query(message)
                .topK(3)
                .similarityThreshold(0.5)
                .build();
        List<Document> documents = storeService.search(request);
        String ragText = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining());
        PromptTemplate template = new PromptTemplate("""
                基于以下上下文: {context},
                回答问题: {question}
                """);
        Prompt prompt = template.create(Map.of("context", ragText, "question", message));
        //调用模型
        ChatResponse response = this.chatModel.call(prompt);
        return BasicVo.success(response.getResults().get(0).getOutput().getText());
    }


    @GetMapping("/advisorRagChat")
    public BasicVo<String> advisorRagChat(@RequestParam(value = "message") String message) {
        Advisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(builder()
                        .similarityThreshold(0.50)
                        .vectorStore(storeService.getVectorStore())
                        .build())
                .build();

        ChatClient chatClient = ChatClient.builder(chatModel).build();
        String answer = chatClient.prompt()
                .advisors(retrievalAugmentationAdvisor)
                .user(message)
                .call()
                .content();
        return BasicVo.success(answer);
    }
}

