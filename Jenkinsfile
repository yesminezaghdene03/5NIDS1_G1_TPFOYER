pipeline {
    agent any

    tools {
        maven 'M2_HOME' // Assurez-vous que ce nom correspond à celui configuré dans Jenkins
        jdk 'JAVA_HOME' // Assurez-vous que ce nom correspond à celui configuré dans Jenkins
        SonarQube Scanner 'SonarQube Scanner' // Assurez-vous que ce nom correspond à celui configuré dans Jenkins
    }

    environment {
        SONAR_HOST_URL = 'http://192.168.50.4:9000'
        SONAR_LOGIN = credentials('sonar-token') // Utilisez le jeton d'authentification configuré
    }

    stages {
        stage('Checkout') {
            steps {
                // Récupérer le code source depuis le dépôt Git
                checkout scm
            }
        }
        stage('Build') {
            steps {
                // Construire le projet avec Maven
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                // Exécuter les tests unitaires
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                // Créer le package du projet
                sh 'mvn package'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                // Lancer l'analyse SonarQube
                withSonarQubeEnv('sonarQube') {
                    sh '''
                        sonar-scanner \
                        -Dsonar.projectKey=My_project_key \
                        -Dsonar.projectName="tp-foyer 2" \
                        -Dsonar.projectVersion=1.0 \
                        -Dsonar.sources=src/main/java \
                        -Dsonar.tests=src/test/java \
                        -Dsonar.host.url=$SONAR_HOST_URL \
                        -Dsonar.login=$SONAR_LOGIN \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                        -Dsonar.verbose=true
                    '''
                }
            }
        }
        stage('Deploy to Nexus') {
            steps {
                // Déployer l'artefact sur Nexus
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: 'http://192.168.50.4:8081',
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
    }

    post {
        always {
            // Actions à exécuter après chaque build, succès ou échec
            junit '**/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
        }
        failure {
            // Actions à exécuter en cas d'échec du build
            mail to: 'yesminzaghden@esprit.tn',
                 subject: "Échec du build ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Consultez les détails à ${env.BUILD_URL}"
        }
    }
}
