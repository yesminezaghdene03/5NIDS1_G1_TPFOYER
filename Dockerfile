# Utiliser l'image officielle d'OpenJDK 17 comme base
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Installer ZAP (OWASP Zed Attack Proxy)
RUN apt-get update && apt-get install -y wget && \
    wget https://github.com/zaproxy/zaproxy/releases/download/v2.14.2/ZAP_2.14.2_linux.tar.gz && \
    tar -xvzf ZAP_2.14.2_linux.tar.gz && \
    mv ZAP_2.14.2 /zap

# Copier l'artefact généré par Maven (le fichier JAR) dans le conteneur
COPY ./target/tp-foyer-5.0.0.jar app.jar

# Exposer le port sur lequel l'application écoutera
EXPOSE 8089

# Exposer le port ZAP pour les tests de sécurité
EXPOSE 8090

# Démarrer l'application avec la commande Java
ENTRYPOINT ["java", "-jar", "app.jar"]
