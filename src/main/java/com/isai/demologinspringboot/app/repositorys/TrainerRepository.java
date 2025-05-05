package com.isai.demologinspringboot.app.repositorys;

import com.isai.demologinspringboot.app.models.Trainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository
        extends CrudRepository<Trainer, Integer> {
}
