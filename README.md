# Acabou o Mony - Solutis

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-green)

## Sobre o Projeto

Este repositório contém a solução do projeto Acabou o Mony, desenvolvido como parte do programa Solutis Dev Trail 2024,
como desafio em back-End. O sistema foi projetado para suportar um alto volume de transações financeiras, focando em segurança,
escalabilidade e integração com plataformas.

## Tecnologias Utilizadas
- Spring Boot

- Docker Desktop
  
- Spring Data JPA
  
- API Gateway
  
- RabbitMQ
  
- PostgreSQL
  
## Estrutura do Projeto
```
|-- src                         <- Código fonte do Back-End do sistema.
|   |
|   |-- account-service         <- configurações de conta.
|   |-- integration             <- forma de integrar aplicações.
|   |-- payment-service         <- aplicação para pagamento.
|   |-- security-service        <- aplicação de segurança.
|   |-- email-service           <- serviço de notificação via email.
|
|- README.md                    <- Documentação do projeto.
|- docker-compose.yml           <- Configuração Docker para ambiente local.
```

## Como executar o projeto

- Clone o repositório: ```git clone https://github.com/AlmadaAlmada/Acabou-o-mony-Solutis.git```

- Navegue até o diretório do projeto: ```cd Acabou-o-mony-Solutis```

- Instale as dependências do projeto: ```mvn install```

- Suba o banco de dados PostgreSQL com Docker: ```docker-compose up --build```

## Desenvolvimento
O projeto foi desenvolvido pela equipe, utilizando boas práticas de desenvolvimento e testes para garantir confiabilidade e 
performance.

### Membros da Equipe

- **Alice Lima** - https://github.com/alsoares086 

- **Larissa Sena** - https://github.com/larissacsena

- **Pietra Almeida** - https://github.com/almeidapietra

- **Vinícius Almada** - https://github.com/AlmadaAlmada









