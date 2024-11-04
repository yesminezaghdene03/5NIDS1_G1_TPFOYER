pipeline {
    agent any

    tools {
        git 'Default' // Assurez-vous que 'Default' est bien configuré dans Jenkins
        maven 'M2_Home' // Nom correct pour Maven
        jdk 'JAVA_HOME' // Assurez-vous que 'JAVA_HOME' est bien configuré dans Jenkins
    }

    environment {
        SONAR_HOST_URL = 'http://192.168.50.4:9000'
        SONARQUBE_ENV = 'SonarQube Scanner'
        SONAR_LOGIN = credentials('jenkins-sonar')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'AmineDridia_5NIDS1_G1', 
                    url: 'https://github.com/yesminezaghdene03/5NIDS1_G1_TPFOYER.git'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                script {
                    def scannerHome = tool 'SonarQube Scanner'
                    withSonarQubeEnv('SonarQube') {
                        sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=YourProjectKey -Dsonar.sources=." 
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
