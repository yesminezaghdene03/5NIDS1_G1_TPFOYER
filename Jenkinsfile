pipeline {
    agent any

    tools {
        git 'Default' // Assure-toi que 'Default' correspond à l'installation Git configurée dans Jenkins
        maven 'M2_HOME'
        jdk 'JAVA_HOME'
    }

    environment {
        DOCKER_IMAGE_NAME = 'tp-foyer' // Nom de l'image Docker
        DOCKER_TAG = "5.0.0" // Tag de l'image Docker
        SONAR_HOST_URL = 'http://192.168.50.4:9000' // URL de ton serveur SonarQube
        SONAR_PROJECT_KEY = 'My_project' // Clé de projet SonarQube
        SONAR_PROJECT_NAME = 'tp-foyer 2' // Nom du projet SonarQube
        SONAR_PROJECT_VERSION = '1.0' // Version du projet SonarQube
        SONAR_LOGIN = 'sqp_9233859e77bc9e349efbd11872ad11527f1e745c' // Token d'authentification SonarQube
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'YesminZaghden_NIDS1_G1', url: 'https://github.com/yesminezaghdene03/5NIDS1_G1_TPFOYER.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'  // Installer les dépendances et compiler
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    sh """
                    mvn sonar:sonar \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                        -Dsonar.projectName=${SONAR_PROJECT_NAME} \
                        -Dsonar.projectVersion=${SONAR_PROJECT_VERSION} \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.sources=src/main/java \
                        -Dsonar.tests=src/test/java \
                        -Dsonar.login=${SONAR_LOGIN} \
                        -Dsonar.verbose=true
                    """
                }
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'  // Exécuter les tests
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'  // Créer le package JAR
            }
        }

        stage('Upload Artifact') {
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: '192.168.50.4:8081',
                    groupId: 'tn.esprit',
                    version: '5.0.0',
                    repository: 'tp-foyer2',
                    credentialsId: 'nexus-credentials',
                    artifacts: [
                        [artifactId: 'tp-foyer', classifier: '', file: 'target/tp-foyer-5.0.0.jar', type: 'jar']
                    ]
                )
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_TAG} ."
                }
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'Docker-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    script {
                        sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin"
                        sh "docker push yesmin1/tp-foyer:5.0.0"
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    sh "docker-compose up -d"  // Déployer avec Docker Compose
                }
            }
        }

        stage('Start Prometheus') {
            steps {
                script {
                    sh 'docker start prometheus'  // Démarrer Prometheus
                }
            }
        }

        stage('Start Grafana') {
            steps {
                script {
                    sh 'docker start grafana'  // Démarrer Grafana
                }
            }
        }
    }

    post {
        always {
            sh 'echo "Listing directory contents:"'
            sh 'ls -R target/surefire-reports/'  // Liste les fichiers dans 'surefire-reports'
            junit '**/target/surefire-reports/*.xml'  // Publier les rapports de tests
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true  // Archiver les artefacts générés
        }
        success {
            echo 'Build and deployment successful!'
            mail to: 'yesminzaghden1@gmail.com',
                 subject: "Build Success - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "The build was successful! Check the details at ${env.BUILD_URL}"
        }
        failure {
            mail to: 'yesminzaghden1@gmail.com',
                 subject: "Build Failure - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "The build failed. Please check the details at ${env.BUILD_URL}"
        }
    }
}
