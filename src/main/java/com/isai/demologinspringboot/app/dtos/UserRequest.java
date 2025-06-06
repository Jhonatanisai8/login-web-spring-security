package com.isai.demologinspringboot.app.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Long id;

    @NotBlank(message = "Los Nombres no pueden estar vacíos")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    @Size(max = 50)
    private String lastName;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 10)
    private String userName;

    @NotBlank(message = "El Email no puede estar vacío")
    @Size(max = 40)
    @Email(message = "Correo inválido")
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

    private String gender;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate registrationDate;

    private String pathImageProfile;

    private MultipartFile imageProfile;

    private Set<String> roles;  // O Set<Long> si manejas los IDs de roles
}
