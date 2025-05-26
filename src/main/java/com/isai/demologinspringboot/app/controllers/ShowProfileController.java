package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.models.User;
import com.isai.demologinspringboot.app.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ShowProfileController {

    private final UserRepository userRepository;

    @GetMapping("/profile")
    public ModelAndView showClientProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUserName(userDetails.getUsername()).get();
        return new ModelAndView("client/profile")
                .addObject("user", user);
    }

    @GetMapping("/edit-profile")
    public String showEditProfileForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUserName(userDetails.getUsername()).get();
        model.addAttribute("user", user);
        return "client/edit-profile";
    }

    @PostMapping("/edit-profile")
    public String updateProfile(@ModelAttribute("user") User updatedUser,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        User currentUser = userRepository.findByUserName(userDetails.getUsername()).orElseThrow();

        currentUser.setFirstName(updatedUser.getFirstName());
        currentUser.setLastName(updatedUser.getLastName());
        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setDni(updatedUser.getDni());
        currentUser.setGender(updatedUser.getGender());
        currentUser.setBirthDate(updatedUser.getBirthDate());
        currentUser.setPhone(updatedUser.getPhone());

        userRepository.save(currentUser);

        redirectAttributes.addFlashAttribute("success", "Perfil actualizado correctamente");
        return "redirect:/client/profile";
    }

}
