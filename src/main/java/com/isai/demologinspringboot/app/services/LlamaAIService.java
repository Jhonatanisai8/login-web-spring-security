package com.isai.demologinspringboot.app.services;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LlamaAIService {

    @Autowired
    private OllamaChatModel chatModel;

    public String generateResult(String promt) {
        OllamaOptions options = new OllamaOptions();
        options.setModel("llama2");
        ChatResponse response = chatModel.call(new Prompt(promt, options));
        if (response != null) {
            return response.getResult().getOutput().getText();
        }
        return "Error no hay respuesta recibida de ollama API AI";
    }
}
