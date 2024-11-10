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

        stage('Upload Artifact') { // Stage pour uploader l'artifact sur Nexus
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: '192.168.50.4:8081', // Ajout du protocole http://
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

        stage('Docker Build') { // Stage pour construire l'image Docker
            steps {
                script {
                    sh "docker build -t yesmin1/tp-foyer:5.0.0 ."
                }
            }
        }

        stage('Docker Push') { // Stage pour pousser l'image Docker dans Docker Hub
            steps {
                withCredentials([usernamePassword(credentialsId: 'Docker-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    script {
                        // Se connecter à Docker Hub avec les credentials Jenkins
                        sh "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD"

                        // Pousser l'image dans Docker Hub avec le bon nom d'utilisateur
                        sh "docker push yesmin1/tp-foyer:5.0.0"
                    }
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
            // Debugging step to check the directory structure
            sh 'echo "Listing directory contents:"'
            sh 'ls -R target/surefire-reports/'  // This will list the files in the 'surefire-reports' folder

            // Then, attempt to gather JUnit reports
            junit '**/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
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
