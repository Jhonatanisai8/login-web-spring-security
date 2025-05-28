package com.isai.demologinspringboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ChatController {
    @GetMapping("/ai_chat")
    public String showAIChatPage() {
        return "client/ai-chat";
    }
}
