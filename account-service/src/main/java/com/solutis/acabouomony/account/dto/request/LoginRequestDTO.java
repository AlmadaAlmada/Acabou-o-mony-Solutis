package com.solutis.acabouomony.account.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LoginRequestDTO {

    @NotBlank(message = "O email não pode estar vazio")
    @Email(message = "Senha ou Email inválido")
    private String email;

    @NotBlank(message = "A senha não pode estar vazia")
    @Email(message = "Senha ou Email inválido")
    private String password;

 }
