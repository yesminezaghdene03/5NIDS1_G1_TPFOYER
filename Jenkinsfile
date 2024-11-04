pipeline {
    agent any

    stages {
        stage('GIT') {
            steps {
                git branch: 'AmineDridia_5NIDS1_G1',
                    url: 'https://github.com/yesminezaghdene03/5NIDS1_G1_TPFOYER'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile' // Étape de compilation
            }
        }

        stage('Scan') {
            steps {
                withSonarQubeEnv('sq1') {
                    sh 'mvn sonar:sonar -Dsonar.java.binaries=target/classes' // Analyse SonarQube avec les classes compilées
                }
            }
        }
    }
}
