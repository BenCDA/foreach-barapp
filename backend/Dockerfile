# Étape 1 : builder l'application avec Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copier les fichiers pom.xml et sources
COPY pom.xml .
COPY src ./src

# Compiler et packager l'application (génère un jar)
RUN mvn clean package -DskipTests

# Étape 2 : créer l'image finale pour exécuter le jar
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copier le jar depuis l'étape build
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port (8080 par défaut pour Spring Boot)
EXPOSE 8080

# Commande pour démarrer l'application
ENTRYPOINT ["java","-jar","app.jar"]
