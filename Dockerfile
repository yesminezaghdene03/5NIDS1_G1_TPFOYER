# Utiliser l'image de base openjdk 17 slim
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier l'artefact JAR généré par Maven dans le conteneur
COPY ./target/tp-foyer-5.0.0.jar app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8089

# Démarrer l'application avec la commande java
ENTRYPOINT ["java", "-jar", "app.jar"]
