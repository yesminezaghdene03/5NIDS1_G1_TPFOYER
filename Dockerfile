# Étape 1: Utilisation de l'image de base Maven pour la construction de l'application
FROM maven:3.8.4-openjdk-17 AS build

# Répertoire de travail
WORKDIR /app

# Copier le fichier pom.xml et le répertoire src dans le conteneur
COPY pom.xml .
COPY src ./src

# Construire le package sans les tests
RUN mvn clean package -DskipTests

# Étape 2: Utilisation de l'image de base OpenJDK pour exécuter l'application
FROM openjdk:17-jdk-slim

# Répertoire de travail
WORKDIR /app

# Copier le JAR généré depuis l'étape de build
COPY --from=build /app/target/tp-foyer-5.0.0.jar app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8089

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
