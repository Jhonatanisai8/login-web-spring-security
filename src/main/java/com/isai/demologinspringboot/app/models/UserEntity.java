package com.isai.demologinspringboot.app.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "emailUser"))
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(nullable = false, length = 50)
    private String firstNameUser;

    @Column(nullable = false, length = 50)
    private String lastNameUser;

    @Column(nullable = false, length = 10)
    private String userName;

    @Column(nullable = false, length = 30)
    private String emailUser;

    @Column(nullable = false, length = 50)
    private String passwordUser;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "id_user",
                    referencedColumnName = "idUser"
            ), inverseJoinColumns = @JoinColumn(
            name = "id_rol",
            referencedColumnName = "idRol"
    ))
    private Set<Rol> roles;
}
