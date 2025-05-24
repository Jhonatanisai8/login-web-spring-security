package com.isai.demologinspringboot.app.services.impl;

import com.isai.demologinspringboot.app.models.Trainer;
import com.isai.demologinspringboot.app.repositorys.TrainerRepository;
import com.isai.demologinspringboot.app.services.TrainerCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService implements TrainerCrud {

    private final TrainerRepository repository;

    @Override
    public void saveTrainer(Trainer trainer) {
        repository.save(trainer);
    }

    @Override
    public Trainer findTrainerById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Trainer> findAllTrainers() {
        return repository.findAll();
    }

    @Override
    public void deleteTrainerById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Trainer> findAllTrainers(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
