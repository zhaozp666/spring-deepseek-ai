package com.example.demo.service;


import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private final VectorStore vectorStore;

    public StoreService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void store(List<Document> documentList) {
        vectorStore.accept(documentList);
    }

    public List<Document> search(SearchRequest request) {
        return vectorStore.similaritySearch(request);
    }

    public VectorStore getVectorStore() {
        return vectorStore;
    }

}
