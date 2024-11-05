package com.example.email_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentConfirmationDto(UUID paymentId,
                                     String orderNumber,
                                     UUID userId,
                                     String nameUser,
                                     String emailTo,
                                     BigDecimal value,
                                     String status) {
}
