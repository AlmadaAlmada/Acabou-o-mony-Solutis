package com.solutis.acabouomony.account.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserReqDTO {

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;

    @NotBlank
    private String password;

    @NotBlank
    private String contact;

    @NotBlank
    @Email
    private String email;
}
