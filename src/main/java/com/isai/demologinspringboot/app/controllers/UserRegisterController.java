package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.dtos.UserRequest;
import com.isai.demologinspringboot.app.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserServiceImpl userService;

    @ModelAttribute("user")
    public UserRequest newUserRequest() {
        return new UserRequest();
    }

    @GetMapping(path = "/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping(path = "/register")
    public String processRegisterForm(@ModelAttribute("user") UserRequest userRequest) {
        userService.saveUser(userRequest);
        return "redirect:/register?exito";
    }

}
