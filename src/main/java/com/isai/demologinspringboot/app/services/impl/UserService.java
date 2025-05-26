package com.isai.demologinspringboot.app.services.impl;

import com.isai.demologinspringboot.app.models.User;
import com.isai.demologinspringboot.app.repositorys.UserRepository;
import com.isai.demologinspringboot.app.services.UserCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserCrud {
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}
