package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.models.MembershipUser;
import com.isai.demologinspringboot.app.models.Payment;
import com.isai.demologinspringboot.app.models.User;
import com.isai.demologinspringboot.app.repositorys.UserRepository;
import com.isai.demologinspringboot.app.services.impl.MembershipUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ShowProfileController {

    private final UserRepository userRepository;

    private final MembershipUserService membershipUserService;

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


    @GetMapping("/my_memberships")
    public String showAllUserMemberships(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<MembershipUser> userMemberships = membershipUserService.findAllByUser(user);
        model.addAttribute("userMemberships", userMemberships);
        return "client/my-membership";
    }
    @GetMapping("/my_payments")
    public String myPayments(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Payment> payments = user.getPayments();
        model.addAttribute("payments", payments);
        return "client/my_payments";
    }


}
