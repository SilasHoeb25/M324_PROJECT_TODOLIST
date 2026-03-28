# --- Build-Stage: Maven kompiliert das Projekt ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# pom.xml kopieren und Dependencies vorladen (Cache)
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Restlichen Source-Code kopieren und bauen
COPY src ./src
RUN mvn clean package -DskipTests

# --- Runtime-Stage
FROM eclipse-temurin:17-jre
WORKDIR /app


COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Startkommando
ENTRYPOINT ["java", "-jar", "app.jar"]