package com.isai.demologinspringboot.app.services;


import com.isai.demologinspringboot.app.models.Speciality;

import java.util.List;

public interface SpecialityCrud {
    List<Speciality> findAllSpecialities();
}
