package com.isai.demologinspringboot.app.services.impl;

import com.isai.demologinspringboot.app.dtos.UserRequest;
import com.isai.demologinspringboot.app.models.Rol;
import com.isai.demologinspringboot.app.models.User;
import com.isai.demologinspringboot.app.repositorys.UserRepository;
import com.isai.demologinspringboot.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService<User, UserRequest> {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(UserRequest userRequest) {
        return userRepository.save(convertUserRequestToUser(userRequest));
    }

    private User convertUserRequestToUser(UserRequest userRequest) {
        User user = new User();

        user.setId(userRequest.getId());

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        }

        user.setDni(userRequest.getDni());
        user.setPhone(userRequest.getPhone());
        user.setBirthDate(userRequest.getBirthDate());
        user.setGender(userRequest.getGender());
        user.setRegistrationDate(userRequest.getRegistrationDate());
        user.setPathImageProfile(userRequest.getPathImageProfile());

        // Manejo roles (ejemplo simple)
        if (userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
            Set<Rol> roles = userRequest.getRoles().stream()
                    .map(roleName -> {
                        Rol rol = new Rol();
                        rol.setNameRol(roleName);
                        return rol;
                    })
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        } else {
            // Rol por defecto si no envían roles
            Rol rol = new Rol();
            rol.setNameRol("ROLE_USER");
            user.setRoles(Set.of(rol));
        }


        return user;
    }
}
