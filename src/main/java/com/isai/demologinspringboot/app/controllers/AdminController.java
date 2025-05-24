package com.isai.demologinspringboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @RequestMapping(path = "")
    public String showAdmin() {
        return "admin/home";
    }
}
