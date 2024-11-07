pipeline {
    agent any

    tools {
        git 'Default' // Assurez-vous que 'Default' correspond à l'installation Git configurée dans Jenkins
        maven 'M2_HOME'
        jdk 'JAVA_HOME'
    }

    environment {
        DOCKER_IMAGE_NAME = 'tp-foyer' // Nom de l'image Docker
        DOCKER_TAG = '5.0.0' // Tag de l'image Docker
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

        stage('Docker Build') { // Stage pour construire l'image Docker
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_TAG} ."
                }
            }
        }

        stage('Deploy') { // Stage pour déployer avec Docker Compose
            steps {
                script {
                    sh "docker-compose up -d"
                }
            }
        }

        stage('Start Prometheus') {
            steps {
                script {
                    sh 'docker start prometheus'
                }
            }
        }

        stage('Start Grafana') {
            steps {
                script {
                    sh 'docker start grafana'
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
            echo 'Build and deployment successful!'
        }
        failure {
            mail to: 'yesminzaghden1@gmail.com',
                 subject: "Build Failure - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Please check the details at ${env.BUILD_URL}"
        }
    }
}
