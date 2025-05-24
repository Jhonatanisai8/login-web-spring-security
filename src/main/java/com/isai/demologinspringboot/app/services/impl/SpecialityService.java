package com.isai.demologinspringboot.app.services.impl;

import com.isai.demologinspringboot.app.models.Speciality;
import com.isai.demologinspringboot.app.repositorys.SpecialityRepository;
import com.isai.demologinspringboot.app.services.SpecialityCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialityService implements SpecialityCrud {
    private final SpecialityRepository specialityRepository;

    @Override
    public List<Speciality> findAllSpecialities() {
        return specialityRepository.findAll();
    }

    @Override
    public List<Speciality> findAllSpecialities(Sort pageable) {
        return specialityRepository.findAll(pageable);
    }
}
