package com.isai.demologinspringboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//docker compose -f docker-compose.yml up
//docker exec -it ollama ollama run llama2
@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @RequestMapping(path = "")
    public String showAdmin() {
        return "admin/home";
    }
}
