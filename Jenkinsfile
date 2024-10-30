pipeline {
    agent any

    tools {
        maven 'M2_HOME' // Assurez-vous que ce nom correspond à celui configuré dans Jenkins
        jdk 'JAVA_HOME' // Assurez-vous que ce nom correspond à celui configuré dans Jenkins
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
            environment {
                SONAR_HOST_URL = 'http://192.168.50.4:9000'
                SONAR_LOGIN = 'sqp_a4640d84e1a5c96985c3fcfaea059b6fb44095ce'
            }
            steps {
                // Lancer l'analyse SonarQube
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
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
                    artifactId: 'tp-foyer',
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
