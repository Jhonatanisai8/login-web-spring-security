package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.models.Client;
import com.isai.demologinspringboot.app.models.Speciality;
import com.isai.demologinspringboot.app.repositorys.ClientRepository;
import com.isai.demologinspringboot.app.repositorys.SpecialityRepository;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ClientRepository clientRepository;
    private final SpecialityRepository specialityRepository;
    private final WarehouseServiceImp warehouseServiceImp;

    @GetMapping("")
    public ModelAndView seePageHome(@PageableDefault(sort = "firstName", size = 5) Pageable pageable) {
        Page<Client> clients = clientRepository.findAll(pageable);
        return new ModelAndView("admin/index")
                .addObject("clients", clients);
    }

    @GetMapping("/clients/new")
    public ModelAndView showFormNewCustomer() {
        List<Speciality> specialities = specialityRepository.findAll(Sort.by("nameSpeciality"));
        return new ModelAndView("admin/new-client")
                .addObject("client", new Client())
                .addObject("specialities", specialities);
    }

    @PostMapping("/clients/new")
    public ModelAndView registerCustomer(
            @Valid Client client, BindingResult bindingResult) {

        // Validación de errores y archivo obligatorio
        if (bindingResult.hasErrors() || client.getImageProfile().isEmpty()) {
            if (client.getImageProfile().isEmpty()) {
                bindingResult.rejectValue("imageProfile", "MultipartNotEmpty", "La imagen es obligatoria");
            }
            List<Speciality> specialities = specialityRepository.findAll(Sort.by("nameSpeciality"));
            return new ModelAndView("admin/new-client")
                    .addObject("client", client)
                    .addObject("specialities", specialities);
        }

        // Guardar imagen y cliente
        String imageProfileName = warehouseServiceImp.storeFile(client.getImageProfile());
        client.setPathImageProfile(imageProfileName);
        clientRepository.save(client);

        // Redirigir al inicio del panel admin
        return new ModelAndView("redirect:/admin");
    }
}
