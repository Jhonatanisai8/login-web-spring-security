package com.isai.demologinspringboot.app.services;

import com.isai.demologinspringboot.app.models.Rol;
import com.isai.demologinspringboot.app.models.User;
import com.isai.demologinspringboot.app.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        User userDB = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (Rol rol : userDB.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(rol.getNameRol()));
        }

        return new org.springframework.security.core.userdetails.User(
                userDB.getUserName(),
                userDB.getPassword(),
                authorities
        );
    }
}
