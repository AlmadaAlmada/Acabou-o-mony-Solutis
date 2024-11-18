package com.acabouomony.solutis.email_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class EmailDTO {
    UUID paymentId;
    String orderNumber;
    UUID userId;
    String nameUser;
    String email;
    BigDecimal value;
    String status;
}

