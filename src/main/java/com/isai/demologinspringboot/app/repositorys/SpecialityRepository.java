package com.isai.demologinspringboot.app.repositorys;

import com.isai.demologinspringboot.app.models.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository
        extends JpaRepository<Speciality, Long> {
}
