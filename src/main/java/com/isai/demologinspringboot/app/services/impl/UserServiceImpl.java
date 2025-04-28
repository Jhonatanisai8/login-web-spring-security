package com.isai.demologinspringboot.app.services.impl;

import com.isai.demologinspringboot.app.dtos.UserRequest;
import com.isai.demologinspringboot.app.models.Rol;
import com.isai.demologinspringboot.app.models.UserEntity;
import com.isai.demologinspringboot.app.repositorys.UserRepository;
import com.isai.demologinspringboot.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserServiceImpl
        implements UserService<UserEntity, UserRequest> {

    private final UserRepository userRepository;

    @Override
    public UserEntity saveUser(UserRequest entity) {
        return userRepository.save(convertUserRequestToUserEntity(entity));
    }

    private UserEntity convertUserRequestToUserEntity(UserRequest userRequest) {
        Rol rol = new Rol();
        rol.setNameRol("ROLE_USER");
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstNameUser(userRequest.getFirstNameUser());
        userEntity.setLastNameUser(userRequest.getLastNameUser());
        userEntity.setUserName(userRequest.getUserName());
        userEntity.setEmailUser(userRequest.getEmailUser());
        userEntity.setPasswordUser(userRequest.getPasswordUser());
        userEntity.setRoles(Set.of(rol));
        return userEntity;
    }
}
