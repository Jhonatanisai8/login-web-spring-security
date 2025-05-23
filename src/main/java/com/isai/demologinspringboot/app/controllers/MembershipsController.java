package com.isai.demologinspringboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/admin")
public class MembershipsController {

    @GetMapping("/memberships")
    public ModelAndView showCoaches() {
        return new ModelAndView("admin/memberships/memberships");
    }

}
