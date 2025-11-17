# ============================================
# ETAPA 1: BUILD (Construcción)
# ============================================
FROM maven:3.9.5-eclipse-temurin-17-alpine AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración Maven
COPY pom.xml .
COPY src ./src

# Construir el proyecto (esto genera el JAR)
# -DskipTests: Omite tests para construcción más rápida
RUN mvn clean package -DskipTests

# ETAPA 2: RUNTIME (Ejecución)
FROM eclipse-temurin:17-jre-alpine

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Variable de entorno para Spring profiles
ENV SPRING_PROFILES_ACTIVE=docker

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

# Metadata
LABEL maintainer="santyvarela.03@gmail.com"
LABEL description="Task Manager API - Spring Boot Application"
LABEL version="1.0"