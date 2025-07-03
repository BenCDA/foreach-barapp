# backend/Dockerfile

############################################
# Étape 1 : builder Maven
############################################
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app

# 1. Pré-charger les dépendances pour cacher Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Copier le code et packager
COPY src ./src
RUN mvn clean package -DskipTests -B

############################################
# Étape 2 : image d’exécution
############################################
FROM eclipse-temurin:17-jre

# Créer un groupe et un user non-root sans ambiguïté
RUN groupadd --system appgroup \
 && useradd --system --no-create-home --gid appgroup --home-dir /app appuser

USER appuser
WORKDIR /app

# Copier le JAR depuis l’étape builder
COPY --from=builder /app/target/*-SNAPSHOT.jar app.jar

# Exposer le port Spring Boot
EXPOSE 8080

# Healthcheck via l’Actuator
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=prod"]
