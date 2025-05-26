package com.isai.demologinspringboot.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datos personales
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50)
    private String lastName;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 10)
    private String userName;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Correo inválido")
    @Size(max = 40)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(max = 100)
    private String password;

    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    private String dni;

    @Size(min = 9, max = 9, message = "El teléfono debe tener 9 dígitos")
    private String phone;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @Size(max = 10)
    private String gender;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate registrationDate;

    private String pathImageProfile;

    @Transient
    private MultipartFile imageProfile;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "idRol")
    )
    private Set<Rol> roles;
}
