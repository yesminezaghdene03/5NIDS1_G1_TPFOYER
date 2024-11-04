FROM maven as build
WORKDIR /app
COPY . .
RUN mvn install 



FROM openjdk:17-jdk-slim
# Définir le répertoire de travail dans le conteneur
WORKDIR /app
# Copier le fichier .jar généré par Maven dans le conteneur
COPY --from=build /app/target/tp-foyer.jar /app/
# Exposer le port défini dans application.properties
EXPOSE 8089
# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "tp-foyer.jar"]
