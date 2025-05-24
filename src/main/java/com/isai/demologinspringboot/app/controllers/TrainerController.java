package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.models.Speciality;
import com.isai.demologinspringboot.app.models.Trainer;
import com.isai.demologinspringboot.app.services.impl.SpecialityService;
import com.isai.demologinspringboot.app.services.impl.TrainerService;
import com.isai.demologinspringboot.app.services.impl.WarehouseServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class TrainerController {


    private final TrainerService trainerService;

    private final SpecialityService specialityService;

    private final WarehouseServiceImp warehouseServiceImp;

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

    @PostMapping(path = "/trainers/new")
    public ModelAndView saveTrainer(
            @Valid Trainer trainer,
            BindingResult bindingResult) {
        //validamos
        if (bindingResult.hasErrors() || trainer.getImageProfile().isEmpty()) {
            if (trainer.getImageProfile().isEmpty()) {
                bindingResult.rejectValue("imageProfile", "MultipartNotEmpty", "La imagen es obligatoria");
            }
            List<Speciality> specialitiesBD = specialityService.findAllSpecialities();
            return new ModelAndView("admin/trainers/create-trainer")
                    .addObject("trainer", trainer)
                    .addObject("specialitiesBD", specialitiesBD);
        }

        //guardamos
        String imageProfileName = warehouseServiceImp.storeFile(trainer.getImageProfile());
        trainer.setPathImageProfile(imageProfileName);
        trainerService.saveTrainer(trainer);

        //redirigimos
        return new ModelAndView("redirect:/admin/trainers");
    }

    @GetMapping(path = "trainers/{idTrainer}/edit")
    public ModelAndView showFormEditTrainer(@PathVariable Integer idTrainer) {
        Trainer trainer = trainerService.findTrainerById(idTrainer);
        List<Speciality> specialitiesBD = specialityService.findAllSpecialities();
        return new ModelAndView("admin/trainers/edit-trainer",
                "trainer", trainer)
                .addObject("specialitiesBD", specialitiesBD);
    }

    @PostMapping("/trainers/{idTrainer}/edit")
    public ModelAndView updateTrainer(
            @PathVariable Integer idTrainer,
            @Valid Trainer trainer,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<Speciality> specialitiesBD = specialityService.findAllSpecialities(Sort.by("nameSpeciality"));
            return new ModelAndView("admin/trainers/edit-trainer")
                    .addObject("trainer", trainer)
                    .addObject("specialitiesBD", specialitiesBD);
        }

        Trainer trainerDataBase = trainerService.findTrainerById(idTrainer);

        trainerDataBase.setFirstName(trainer.getFirstName());
        trainerDataBase.setLastName(trainer.getLastName());
        trainerDataBase.setDniTrainer(trainer.getDniTrainer());
        trainerDataBase.setPhone(trainer.getPhone());
        trainerDataBase.setEmail(trainer.getEmail());
        trainerDataBase.setBirthDate(trainer.getBirthDate());
        trainerDataBase.setGender(trainer.getGender());
        trainerDataBase.setDateRegistration(trainer.getDateRegistration());

        if (trainer.getImageProfile() != null && !trainer.getImageProfile().isEmpty()) {
            if (trainerDataBase.getPathImageProfile() != null && !trainerDataBase.getPathImageProfile().isEmpty()) {
                warehouseServiceImp.deleteFile(trainerDataBase.getPathImageProfile());
            }
            String newImageName = warehouseServiceImp.storeFile(trainer.getImageProfile());
            trainerDataBase.setPathImageProfile(newImageName);
        }

        trainerDataBase.setSpecialities(trainer.getSpecialities());

        trainerService.saveTrainer(trainerDataBase);

        return new ModelAndView("redirect:/admin/trainers");
    }


}
