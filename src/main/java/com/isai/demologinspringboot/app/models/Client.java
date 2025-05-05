package com.isai.demologinspringboot.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
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
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(max = 8)
    private String dniClient;

    @Size(max = 9)
    private String phone;

    private String pathImageProfile;

    @Email
    @NotBlank
    @Size(max = 40)
    private String email;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    @Size(max = 1)
    private char gender;

    @NotNull
    private LocalDate dateRegistration;

    @Transient
    private MultipartFile imageProfile;
}
