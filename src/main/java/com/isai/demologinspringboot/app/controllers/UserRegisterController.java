package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.dtos.UserRequest;
import com.isai.demologinspringboot.app.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/register")
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserServiceImpl userService;

    @ModelAttribute("user")
    public UserRequest newUserRequest() {
        return new UserRequest();
    }

    @GetMapping
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping
    public String processRegisterForm(@ModelAttribute("user") UserRequest userRequest, Model model) {
        userService.saveUser(userRequest);
        return "redirect:/register?exito";
    }

}
