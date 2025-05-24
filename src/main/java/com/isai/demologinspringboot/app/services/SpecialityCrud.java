package com.isai.demologinspringboot.app.services;


import com.isai.demologinspringboot.app.models.Speciality;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SpecialityCrud {
    List<Speciality> findAllSpecialities();

    List<Speciality> findAllSpecialities(Sort pageable);

}
