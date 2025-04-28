package com.isai.demologinspringboot.app.services;

import com.isai.demologinspringboot.app.models.Rol;
import com.isai.demologinspringboot.app.models.UserEntity;
import com.isai.demologinspringboot.app.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImplementation implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userDB = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //autoridades (roles) para el usuario
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        userDB.getRoles()
                .forEach(rol -> {
                    authorities.add(new SimpleGrantedAuthority(rol.getNameRol()));
                });

        //user de spring security
        return new User(
                userDB.getUserName(),
                userDB.getPasswordUser(),
                authorities
        );
    }

}
