package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.models.MembershipType;
import com.isai.demologinspringboot.app.models.User;
import com.isai.demologinspringboot.app.services.impl.MembershipTypeService;
import com.isai.demologinspringboot.app.services.impl.MembershipUserService;
import com.isai.demologinspringboot.app.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/client")
public class MembershipUserController {

    private final UserService userService;

    private final MembershipUserService membershipUserService;

    private final MembershipTypeService membershipTypeService;

    @RequestMapping(path = "/buy-membership",method = RequestMethod.POST)
    private String buyMembership(
            @RequestParam("idMembershipType") Integer idMembershipType,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        try {
            MembershipType membershipType = membershipTypeService.findMembershipTypeById(idMembershipType);
            String username = principal.getName();
            User user = userService.findByUserName(username).get();
            //logica
            membershipUserService.assignMembershipToUser(user, membershipType);
            redirectAttributes.addFlashAttribute("success", "¡Membresía adquirida con éxito!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al adquirir la membresía: " + e.getMessage());
        }
        return "redirect:/admin/memberships";
    }
}
