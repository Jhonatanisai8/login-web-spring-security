package com.isai.demologinspringboot.app.dtos;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Long idUser;

    private String firstNameUser;

    private String lastNameUser;

    private String userName;

    private String emailUser;

    private String passwordUser;
}
