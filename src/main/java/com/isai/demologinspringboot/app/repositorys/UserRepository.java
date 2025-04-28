package com.isai.demologinspringboot.app.repositorys;

import com.isai.demologinspringboot.app.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);
}
