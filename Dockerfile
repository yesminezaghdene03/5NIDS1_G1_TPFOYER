FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier l'artefact généré par Maven dans le conteneur
COPY ./target/tp-foyer-5.0.0.jar app.jar

# Installer Fail2Ban
RUN apt-get update && apt-get install -y fail2ban

# Créer les répertoires nécessaires pour les fichiers de configuration Fail2Ban
RUN mkdir -p /etc/fail2ban/filter.d

# Ajouter les fichiers de configuration Fail2Ban à partir de ton projet
COPY fail2ban/jail.local /etc/fail2ban/jail.local
COPY fail2ban/jenkins.conf /etc/fail2ban/filter.d/jenkins.conf

# Exposer le port sur lequel l'application écoute
EXPOSE 8089

# Lancer Fail2Ban et l'application Java en même temps
CMD service fail2ban start && java -jar app.jar
