pipeline {
    agent any
    
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'hadhemiOmrani_5NIDS_G1', url: 'https://github.com/yesminezaghdene03/5NIDS1_G1_TPFOYER.git'
            }
        }
        
        // Ajoutez vos autres étapes de test, compilation, analyse, etc.

        stage('Stop Existing MySQL') {
            steps {
                script {
                    sh 'docker ps -q --filter "publish=3306" | xargs -r docker stop'
                }
            }
        }

        stage('Docker Compose Up') {
            steps {
                script {
                    sh 'docker compose up -d'
                }
            }
        }

        // Ajoutez vos étapes pour Prometheus et Grafana, comme spécifié

        stage('Cleanup Old Docker Images') {
            steps {
                script {
                    sh 'docker image prune -f'
                }
            }
        }
    }
    post {
        always {
            mail to: 'hadhemi5050@gmail.com',
            subject: "Jenkins Build Notification: ${currentBuild.fullDisplayName}",
            body: """
                Build Status: ${currentBuild.currentResult}
                Project: ${env.JOB_NAME}
                Build Number: ${env.BUILD_NUMBER}
                Build URL: ${env.BUILD_URL}
            """
        }
    }
}
