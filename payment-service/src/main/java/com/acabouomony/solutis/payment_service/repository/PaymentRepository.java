package com.acabouomony.solutis.payment_service.repository;

import com.acabouomony.solutis.payment_service.model.PaymentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, UUID> {

    Optional<Page<PaymentModel>> findAllByCpf(String cpf, Pageable pageable);
}