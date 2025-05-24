package com.isai.demologinspringboot.app.services;

import com.isai.demologinspringboot.app.models.Trainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrainerCrud {
    void saveTrainer(Trainer trainer);

    Trainer findTrainerById(Integer id);

    List<Trainer> findAllTrainers();

    void deleteTrainerById(Integer id);

    Page<Trainer> findAllTrainers(Pageable pageable);
}
