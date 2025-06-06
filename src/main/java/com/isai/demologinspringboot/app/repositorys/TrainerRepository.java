package com.isai.demologinspringboot.app.repositorys;

import com.isai.demologinspringboot.app.models.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository
        extends JpaRepository<Trainer, Integer> {
}
