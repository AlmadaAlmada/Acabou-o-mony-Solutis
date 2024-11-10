package com.solutis.acabouomony.account.repository;

import com.solutis.acabouomony.account.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository <User, UUID>{
    boolean existsByEmail(@NotBlank @Email String email);

    Optional<User> findByEmail(String email);

    boolean existsByCpf(String cpf);
}
