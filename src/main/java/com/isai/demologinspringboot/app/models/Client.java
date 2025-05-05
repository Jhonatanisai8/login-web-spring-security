package com.isai.demologinspringboot.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClient;
    @Size(max = 50)
    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;

    @Size(max = 50)
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;

    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    @NotBlank(message = "El DNI es obligatorio")
    private String dniClient;

    @Size(min = 9, max = 9, message = "El teléfono debe tener 9 dígitos")
    @NotBlank(message = "El teléfono es obligatorio")
    private String phone;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    @Size(max = 40)
    private String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate birthDate;

    @NotNull(message = "El género es obligatorio")
    private Character gender;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "La fecha de registro es obligatoria")
    private LocalDate dateRegistration;

    private String PathImageProfile;

    @Transient
    private MultipartFile imageProfile;
}
