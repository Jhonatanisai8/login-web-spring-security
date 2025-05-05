package com.isai.demologinspringboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistryController {

    @GetMapping(path = "/login")
    public String initSession(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }
    @GetMapping(path = "/")
    public String initPage(Model model) {
        model.addAttribute("title", "Login");
        return "redirect:admin";
    }
}
