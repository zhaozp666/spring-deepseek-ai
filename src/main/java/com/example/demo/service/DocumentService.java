package com.example.demo.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Value("classpath:prompt.txt")
    private Resource resource;

    public List<Document> loadDocuments() {
        TextReader textReader = new TextReader(resource);
        List<Document> documents = textReader.read();

        // 分词器
        TokenTextSplitter splitter = new TokenTextSplitter();
        return splitter.apply(documents);
    }
}
