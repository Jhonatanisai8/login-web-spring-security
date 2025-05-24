package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.models.Speciality;
import com.isai.demologinspringboot.app.models.Trainer;
import com.isai.demologinspringboot.app.services.impl.SpecialityService;
import com.isai.demologinspringboot.app.services.impl.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class TrainerController {


    private final TrainerService trainerService;

    private final SpecialityService specialityService;

    @GetMapping("/trainers")
    public ModelAndView showCoaches(@PageableDefault(sort = "firstName", size = 5) Pageable pageable) {
        Page<Trainer> trainersPage = trainerService.findAllTrainers(pageable);
        return new ModelAndView("admin/trainers/show",
                "trainersPage", trainersPage);
    }

    @GetMapping("/trainers/new")
    public ModelAndView showFormNewTrainer() {
        List<Speciality> specialitiesBD = specialityService.findAllSpecialities();
        return new ModelAndView("admin/trainers/create-trainer",
                "trainer", new Trainer())
                .addObject("specialitiesBD", specialitiesBD);
    }
}
