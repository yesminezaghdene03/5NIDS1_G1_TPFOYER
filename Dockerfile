FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier l'artefact généré par Maven dans le conteneur
COPY ./target/tp-foyer-5.0.0.jar app.jar

# Installer Fail2Ban et supervisor
RUN apt-get update && apt-get install -y \
    fail2ban \
    supervisor \
    && rm -rf /var/lib/apt/lists/*

# Créer les répertoires nécessaires pour les fichiers de configuration Fail2Ban
RUN mkdir -p /etc/fail2ban/filter.d

# Ajouter les fichiers de configuration Fail2Ban à partir de ton projet
COPY fail2ban/jail.local /etc/fail2ban/jail.local
COPY fail2ban/jenkins.conf /etc/fail2ban/filter.d/jenkins.conf

# Créer un fichier de configuration supervisor pour gérer Fail2Ban et l'application
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# Exposer le port sur lequel l'application écoute
EXPOSE 8089

# Lancer supervisord pour gérer Fail2Ban et l'application Java
CMD ["/usr/bin/supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]
