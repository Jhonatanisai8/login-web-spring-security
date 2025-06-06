package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.dtos.UserRequest;
import com.isai.demologinspringboot.app.services.impl.UserServiceImpl;
import com.isai.demologinspringboot.app.services.impl.WarehouseServiceImp;
import com.isai.demologinspringboot.app.validation.UserValidador;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserServiceImpl userService;

    private final UserValidador userValidador;
    private final WarehouseServiceImp warehouseServiceImp;

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

        if (userRequest.getImageProfile() == null || userRequest.getImageProfile().isEmpty()) {
            bindingResult.rejectValue("imageProfile", "MultipartNotEmpty", "La imagen de perfil es obligatoria");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        // Almacenar imagen de perfil
        String imageProfileName = warehouseServiceImp.storeFile(userRequest.getImageProfile());
        userRequest.setPathImageProfile(imageProfileName);
        LocalDate date = LocalDate.now();
        userRequest.setRegistrationDate(date);
        // Guardar usuario
        userService.saveUser(userRequest);

        return "redirect:/register?exito";
    }


}
