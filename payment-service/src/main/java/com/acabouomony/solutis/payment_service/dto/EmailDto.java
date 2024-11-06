package com.acabouomony.solutis.payment_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record EmailDto(UUID paymentId,
                       String orderNumber,
                       UUID userId,
                       String nameUser,
                       String emailTo,
                       BigDecimal value,
                       String status) {
}
