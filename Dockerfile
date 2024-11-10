# Utiliser l'image de base openjdk 17 slim
FROM openjdk:17-jdk-slim

# Mettre à jour les dépôts et installer curl
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier l'artefact JAR généré par Maven dans le conteneur
COPY ./target/tp-foyer-5.0.0.jar app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8089

# Ajouter un HEALTHCHECK pour vérifier l'état de l'application
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl --silent --fail http://localhost:8089/health || exit 1

# Démarrer l'application avec la commande java
ENTRYPOINT ["java", "-jar", "app.jar"]
