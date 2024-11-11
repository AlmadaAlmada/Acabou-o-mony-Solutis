package com.solutis.acabouomony.account.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDTO {
    private UUID userId;

    private String name;

    private String cpf;

    private String contact;

    private String email;
}
