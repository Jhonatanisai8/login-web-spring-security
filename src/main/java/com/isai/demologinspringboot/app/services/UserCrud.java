package com.isai.demologinspringboot.app.services;

import com.isai.demologinspringboot.app.models.User;

import java.util.Optional;

public interface UserCrud {
    Optional<User> findByUserName(String userName);
}
