# Etapa de build
FROM maven:latest AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de runtime
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/target/security-service-0.0.1-SNAPSHOT.jar /app/security-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "security-service.jar"]