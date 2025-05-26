package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.models.User;
import com.isai.demologinspringboot.app.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ShowProfile {

    private final UserRepository userRepository;

    @GetMapping("/profile")
    public ModelAndView showClientProfile(@AuthenticationPrincipal UserDetails userDetails) {
        // Buscar el usuario completo desde tu base de datos usando el correo
        User user = userRepository.findByUserName(userDetails.getUsername()).get();
        return new ModelAndView("client/profile")
                .addObject("user", user);
    }


}
