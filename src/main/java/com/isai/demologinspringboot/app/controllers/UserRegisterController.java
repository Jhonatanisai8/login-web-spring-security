package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.dtos.UserRequest;
import com.isai.demologinspringboot.app.services.impl.UserServiceImpl;
import com.isai.demologinspringboot.app.validation.UserValidador;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserRegisterController {

    private final UserServiceImpl userService;

    private final UserValidador userValidador;

    @Autowired
    public UserRegisterController(UserServiceImpl userService, UserValidador userValidador) {
        this.userService = userService;
        this.userValidador = userValidador;
    }

    @ModelAttribute("user")
    public UserRequest newUserRequest(Model model) {
        model.addAttribute("title", "Regístrate");
        return new UserRequest();
    }

    @GetMapping(path = "/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping(path = "/register")
    public String processRegisterForm(
            @ModelAttribute("user") @Valid UserRequest userRequest,
            BindingResult bindingResult,
            Model model) {
        model.addAttribute("title", "Regístrate");
        userValidador.validate(userRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register";
        }
        model.addAttribute("user", userRequest);
        userService.saveUser(userRequest);
        return "redirect:/register?exito";
    }

}
