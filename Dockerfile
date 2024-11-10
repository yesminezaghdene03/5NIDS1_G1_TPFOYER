# Utiliser l'image de base openjdk 17 slim
FROM openjdk:17-jdk-slim

# Installer curl
RUN apt-get update && apt-get install -y curl

# Définir le répertoire de travail
WORKDIR /app

# Copier l'artefact JAR généré par Maven dans le conteneur
COPY ./target/tp-foyer-5.0.0.jar app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8089

# Ajouter un HEALTHCHECK pour vérifier l'état de l'application
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl -f http://localhost:8089/health || exit 1

# Démarrer l'application avec la commande java
ENTRYPOINT ["java", "-jar", "app.jar"]
