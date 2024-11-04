pipeline {
    agent any

    tools {
        git 'Default' // Assurez-vous que 'Default' correspond à l'installation Git configurée dans Jenkins
        maven 'M2_HOME' // Assurez-vous que 'M2_HOME' est le nom configuré pour Maven
        jdk 'JAVA_HOME' // Assurez-vous que 'JAVA_HOME' est le nom configuré pour le JDK
    }

    environment {
        SONAR_HOST_URL = 'http://192.168.50.4:9000' // URL du serveur SonarQube
        SONARQUBE_ENV = 'SonarQube Scanner' // Nom du scanner SonarQube configuré dans Jenkins
        SONAR_LOGIN = credentials('sonar-token') // ID des credentials pour le token SonarQube
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
                    def scannerHome = tool 'SonarQube Scanner' // Nom du scanner configuré dans Jenkins
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
