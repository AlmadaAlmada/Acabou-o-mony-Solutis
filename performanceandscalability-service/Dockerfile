# Usar uma imagem base com JDK 21
FROM eclipse-temurin:21-jdk

# Configurar diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR para o contêiner
COPY target/performanceandscalability-service-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta 8080 (ou qualquer outra que o serviço esteja configurado para usar)
EXPOSE 8081

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]