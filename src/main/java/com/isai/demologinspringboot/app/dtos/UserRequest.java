package com.isai.demologinspringboot.app.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserRequest {

    private Long idUser;

    @NotEmpty(message = "Los Nombres no pueden estar Vacio")
    @Size(max = 50)
    private String firstNameUser;

    @Size(max = 50)
    @NotBlank(message = "Los apellidos no pueden estar Vacio")
    private String lastNameUser;

    private String userName;

    @NotBlank(message = "El Email no puede estar Vacio")
    @Size(max = 50)
    @Email
    private String emailUser;

    @NotBlank(message = "El nombre no puede estar Vacio")
    @Size(max = 100)
    private String passwordUser;
}
