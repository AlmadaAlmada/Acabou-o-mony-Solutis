package com.acabouomony.solutis.email_service.dto;

public record ConfirmationCodeDTO(
        String email,
        String code
) {
}
