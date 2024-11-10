package com.solutis.acabouomony.security.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record MultiFactorAuthDTO(
        @NotNull @Size(min = 6, max = 6) String verificationCode,
        @NotNull UUID userId
) {}