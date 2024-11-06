# Étape 1: Utiliser une image de base officielle de Maven pour construire l'application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2: Utiliser une image de base officielle de OpenJDK pour exécuter l'application
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copier l'artefact généré par Maven dans le conteneur
COPY --from=build /app/target/tp-foyer-5.0.0.jar app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8089

# Démarrer l'application avec la commande java
ENTRYPOINT ["java", "-jar", "app.jar"]
