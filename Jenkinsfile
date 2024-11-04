pipeline {
    agent any

    stages {
        stage('GIT') {
            steps {
                git branch: 'AmineDridia_5NIDS1_G1',
                    url: 'https://github.com/yesminezaghdene03/5NIDS1_G1_TPFOYER'
            }
        }

        stage('Scan') {
            steps {
                withSonarQubeEnv('sq1') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
}
