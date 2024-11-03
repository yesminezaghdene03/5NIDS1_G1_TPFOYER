pipeline {
    agent any

    tools {
        git 'Default'
        maven 'M2_HOME'
        jdk 'JAVA_HOME'
    }

    environment {
        SONAR_HOST_URL = 'http://192.168.50.4:9000'
        SONAR_LOGIN = credentials('sqp_9233859e77bc9e349efbd11872ad11527f1e745c')
        SONAR_PROJECT_KEY = 'My_project'
        SONAR_PROJECT_NAME = 'tp-foyer 2'
        SONAR_PROJECT_VERSION = '1.0'
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
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube Scanner') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Deploy to Nexus') {
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: NEXUS_URL,
                    groupId: NEXUS_GROUP_ID,
                    version: NEXUS_VERSION,
                    repository: NEXUS_REPO,
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
            junit '**/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
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