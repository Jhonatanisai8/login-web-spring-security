package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.services.LlamaAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my-ai-service") // ¡Esto cambiaría la URL!
public class OllamaController {

    @Autowired
    private LlamaAIService llamaAIService;

    @GetMapping(path = "/api/ai/v1/generate")
    public String generateRest(@RequestParam(value = "promptMessage") String promptMessage) {
        promptMessage.concat(" la respuesta en español por favor.");
        System.out.println(promptMessage);
        return llamaAIService.generateResult(promptMessage);
    }
}
