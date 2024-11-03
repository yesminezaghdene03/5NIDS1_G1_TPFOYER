pipeline {
    agent any

    tools {
        git 'Default' // Assurez-vous que 'Default' correspond à l'installation Git configurée dans Jenkins
        maven 'M2_HOME'
        jdk 'JAVA_HOME'
    }

    environment {
        SONAR_HOST_URL = 'http://192.168.50.4:9000'
        SONARQUBE_ENV = 'SonarQube Scanner'
        SONAR_LOGIN = credentials('sonar-token')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'YesminZaghden_NIDS1_G1', url: 'https://github.com/yesminezaghdene03/5NIDS1_G1_TPFOYER.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('UploadArtifact') {
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: 'http://192.168.50.4:8081', // Ajout du protocole http://
                    groupId: 'tn.esprit',
                    version: '5.0.0', // Assurez-vous que cette version est correcte pour votre déploiement
                    repository: 'tp-foyer2',
                    credentialsId: 'nexus-credentials', // Utilisez l'ID configuré ici
                    artifacts: [
                        [artifactId: 'tp-foyer', classifier: '', file: 'target/tp-foyer-5.0.0.jar', type: 'jar']
                    ]
                )
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv(SONARQUBE_ENV) {
                    sh '''
                        sonar-scanner \
                        -Dsonar.projectKey=tp-foyer \
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

        stage('Verify Nexus Configuration') {
            steps {
                script {
                    echo 'Vérifiez la configuration du dépôt Nexus pour permettre le déploiement des artefacts.'
                    echo '1. Accédez à l\'interface d\'administration de Nexus.'
                    echo '2. Naviguez vers "Repositories" et sélectionnez "tp-foyer2".'
                    echo '3. Vérifiez que la politique de déploiement permet les mises à jour.'
                    echo '4. Assurez-vous que les permissions sont correctement définies.'
                    echo '5. Sauvegardez les modifications si nécessaire.'
                }
            }
        }
    }

    post {
        always {
            junit '*/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: '*/target/*.jar', allowEmptyArchive: true
        }
        success {
            echo 'Analyse SonarQube et déploiement sur Nexus réussis!'
        }
        failure {
            mail to: 'yesminzaghden1@gmail.com',
                 subject: "Échec du build ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Consultez les détails à ${env.BUILD_URL}"
        }
    }
}