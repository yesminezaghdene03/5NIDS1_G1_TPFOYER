FROM openjdk:17-jdk-slim
WORKDIR /app
# Copier l'artefact généré par Maven dans le conteneur
COPY ./target/tp-foyer-5.0.0.jar app.jar
# tt
# Exposer le port sur lequel l'application écoute
EXPOSE 8089

# Démarrer l'application avec la commande java
ENTRYPOINT ["java", "-jar", "app.jar"]
