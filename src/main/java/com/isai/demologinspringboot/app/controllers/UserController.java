package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.models.Rol;
import com.isai.demologinspringboot.app.models.User;
import com.isai.demologinspringboot.app.models.Speciality;
import com.isai.demologinspringboot.app.repositorys.UserRepository;
import com.isai.demologinspringboot.app.repositorys.SpecialityRepository;
import com.isai.demologinspringboot.app.services.impl.WarehouseServiceImp;
import com.isai.demologinspringboot.app.validation.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {

    private final UserRepository userRepository;
    private final SpecialityRepository specialityRepository;
    private final WarehouseServiceImp warehouseServiceImp;

    @GetMapping(path = "/clients")
    public ModelAndView seePageHome(@PageableDefault(sort = "firstName", size = 5) Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);  // Solo CLIENTES
        return new ModelAndView("admin/clients/show")
                .addObject("users", users);
    }

    @GetMapping("/clients/new")
    public ModelAndView showFormNewCustomer() {
        List<Speciality> specialities = specialityRepository.findAll(Sort.by("nameSpeciality"));
        return new ModelAndView("admin/clients/create-client")
                .addObject("user", new User())
                .addObject("specialities", specialities);
    }

    @PostMapping("/clients/new")
    public ModelAndView registerCustomer(@Valid @ModelAttribute("client") User client, BindingResult bindingResult) {

        if (bindingResult.hasErrors() || client.getImageProfile().isEmpty()) {
            if (client.getImageProfile().isEmpty()) {
                bindingResult.rejectValue("imageProfile", "MultipartNotEmpty", "La imagen es obligatoria");
            }
            List<Speciality> specialities = specialityRepository.findAll(Sort.by("nameSpeciality"));
            return new ModelAndView("admin/clients/create-client")
                    .addObject("user", client)
                    .addObject("specialities", specialities);
        }

        String imageProfileName = warehouseServiceImp.storeFile(client.getImageProfile());
        client.setPathImageProfile(imageProfileName);
        LocalDate date = LocalDate.now();
        client.setRegistrationDate(date);
        Rol rol = new Rol();
        rol.setNameRol("ROLE_USER");
        client.setRoles(Set.of(rol));
        client.setPassword(Utils.encryptPassword(client.getPassword()));
        userRepository.save(client);


        return new ModelAndView("redirect:/admin/clients");
    }

    @GetMapping(path = "/clients/{id}/edit")
    public ModelAndView showFormEditClient(@PathVariable Long id) {
        User userOptional = userRepository.findById(id).get();
        return new ModelAndView("admin/clients/edit-client")
                .addObject("user", userOptional);
    }

    @PostMapping(path = "clients/{id}/edit")
    public ModelAndView editClient(@PathVariable Long id,
                                   @Valid @ModelAttribute("user") User userForm,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin/clients/edit-client")
                    .addObject("user", userForm);
        }

        User clientDataBase = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado para actualizar"));

        clientDataBase.setFirstName(userForm.getFirstName());
        clientDataBase.setLastName(userForm.getLastName());
        clientDataBase.setDni(userForm.getDni());
        clientDataBase.setPhone(userForm.getPhone());
        clientDataBase.setEmail(userForm.getEmail());
        clientDataBase.setBirthDate(userForm.getBirthDate());
        clientDataBase.setGender(userForm.getGender());

        if (userForm.getImageProfile() != null && !userForm.getImageProfile().isEmpty()) {
            if (clientDataBase.getPathImageProfile() != null && !clientDataBase.getPathImageProfile().isEmpty()) {
                warehouseServiceImp.deleteFile(clientDataBase.getPathImageProfile());
            }
            String newImagePath = warehouseServiceImp.storeFile(userForm.getImageProfile());
            clientDataBase.setPathImageProfile(newImagePath);
        }
        LocalDate date = LocalDate.now();
        clientDataBase.setRegistrationDate(date);
        userRepository.save(clientDataBase);
        return new ModelAndView("redirect:/admin/clients");
    }

    @GetMapping(path = "/clients/{id}/remove")
    public String removeClient(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/clients";
    }
}
