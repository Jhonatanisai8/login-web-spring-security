package com.isai.demologinspringboot.app.repositorys;

import com.isai.demologinspringboot.app.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository
        extends JpaRepository<Client, Integer> {
    Client findAllByIdClient(Integer idClient);

    Client findByIdClient(Integer idClient);
}
