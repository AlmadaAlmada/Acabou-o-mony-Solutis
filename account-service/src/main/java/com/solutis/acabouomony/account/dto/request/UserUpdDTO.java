package com.solutis.acabouomony.account.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class UserUpdDTO {

    @NotNull
    private UUID userId;

    private String name;

    private String contact;

    private String email;
}

