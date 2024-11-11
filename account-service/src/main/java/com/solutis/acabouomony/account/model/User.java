package com.solutis.acabouomony.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;  // ID do usuário gerado automaticamente

    private String name;  // Nome do usuário

    @Column(length = 11)
    private String cpf;  // CPF do usuário (formato brasileiro)

    private String contact;  // Informações de contato do usuário (telefone, por exemplo)

    private String email;  // Email do usuário

    @JsonIgnore
    private final String password;  // Senha do usuário, será ignorada ao ser serializada

    @JsonIgnore
    private final String secret;  // Informação secreta, também ignorada na serialização

    @JsonIgnore
    private final String answer;  // Resposta para a pergunta secreta, ignorada na serialização


}
