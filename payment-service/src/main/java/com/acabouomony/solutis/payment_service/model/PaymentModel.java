package com.acabouomony.solutis.payment_service.model;

import com.acabouomony.solutis.payment_service.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "payments")
public class PaymentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentId;

    private String orderNumber;

    private BigDecimal value;

    private String cpf;

    private String nameCard;

    private String number;

    private String dueDate;

    private String code;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDateTime paymentDate;

    private UUID userId;

    private String nameUser;

    private String email;
}
