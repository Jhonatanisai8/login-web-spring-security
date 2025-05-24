package com.isai.demologinspringboot.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trainers")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTrainer;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(max = 8)
    private String dniTrainer;

    @Size(max = 9)
    private String phone;

    private String pathImageProfile;

    @Email
    @NotBlank
    @Size(max = 40)
    private String email;

    @NotNull
    private LocalDate birthDate;

    @NotNull(message = "El género es obligatorio")
    @Size(max = 10)
    private String gender;

    @NotNull
    private LocalDate dateRegistration;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "trainers_specialitys",
            joinColumns = @JoinColumn(name = "id_trainer"),
            inverseJoinColumns = @JoinColumn(name = "id_speciality"))
    private Set<Speciality> specialities;

    @Transient
    private MultipartFile imageProfile;
}
