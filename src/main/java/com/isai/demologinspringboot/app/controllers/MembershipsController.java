package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.models.MembershipType;
import com.isai.demologinspringboot.app.services.impl.MembershipTypeService;
import com.isai.demologinspringboot.app.services.impl.WarehouseServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class MembershipsController {

    private final MembershipTypeService membershipTypeService;

    private final WarehouseServiceImp warehouseServiceImp;

    @GetMapping(path = "/memberships")
    public ModelAndView listMoviesHome(
            @PageableDefault(sort = "price", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<MembershipType> membershipTypesPage = membershipTypeService.findAllMembershipTypes(pageable);
        return new ModelAndView("admin/memberships/show")
                .addObject("membershipTypesPage", membershipTypesPage);
    }

    @GetMapping(path = "/memberships/new")
    public ModelAndView showFormNewMembershipType() {
        return new ModelAndView("admin/memberships/create-membershipType")
                .addObject("membershipType", new MembershipType());
    }

    @PostMapping(path = "/memberships/new")
    public ModelAndView saveMembershipType(
            @Validated MembershipType membershipType,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors() || membershipType.getImageProfile().isEmpty()) {
            if (membershipType.getImageProfile().isEmpty()) {
                bindingResult.rejectValue("imageProfile",
                        "MultipartNotEmpty", "La imagen es obligatoria SMS.");
            }
            return new ModelAndView("admin/memberships/create-membershipType")
                    .addObject("membershipType", membershipType);
        }

        //guardamos
        String imageProfileName = warehouseServiceImp.storeFile(membershipType.getImageProfile());
        membershipType.setPathImageProfile(imageProfileName);
        membershipTypeService.saveMembershipType(membershipType);
        return new ModelAndView("redirect:/admin/memberships");
    }

}
