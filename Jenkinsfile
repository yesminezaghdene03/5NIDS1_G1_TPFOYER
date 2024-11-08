pipeline {
    agent any

    stages {
        stage ('Git Checkout') {
            steps {
                git branch: 'hadhemiOmrani_5NIDS_G1', url: 'https://github.com/yesminezaghdene03/5NIDS1_G1_TPFOYER.git'
            }
        }
        stage ('UNIT Testing') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage ('Integration Testing') {
            steps {
                sh 'mvn verify'
            }
        }
        stage ('Maven Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Code Coverage') {
            steps {
                jacoco()
               
            }
        }

        stage ('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv(credentialsId: 'sonar-api-key') {
                        sh 'mvn clean package sonar:sonar'
                    }
                }
            }
        }
        stage ('Quality Gate Status') {
            steps {
                script {
                    waitForQualityGate abortPipeline: false, credentialsId: 'sonar-api-key'
                }
            }
        }
        stage ('Nexus') {
            steps {
                script {

                    def readPomVersion = readMavenPom file: 'pom.xml'
                    def version = readPomVersion.version
                    def repository = version.endsWith("SNAPSHOT") ? "tpfoyer-snapshot" : "Tpfoyer-Release"

                    nexusArtifactUploader artifacts: [
                        [
                            artifactId: 'tp-foyer', 
                            classifier: '', 
                            file: 'target/Uterk.jar', 
                            type: 'jar'
                        ]
                    ], 
                    credentialsId: 'nexus-auth', 
                    groupId: 'tn.esprit', 
                    nexusUrl: '192.168.1.170:8081', 
                    nexusVersion: 'nexus3', 
                    protocol: 'http', 
                    repository: repository, 
                    version: "${readPomVersion.version}"
                }
            }
        }
        stage('Docker Image  Build'){
            steps {
                script {
                    sh 'docker image build -t $JOB_NAME:v1.$BUILD_ID .'
                    sh 'docker image tag $JOB_NAME:v1.$BUILD_ID omranihadhemi/$JOB_NAME:v1.$BUILD_ID'
                    sh 'docker image tag $JOB_NAME:v1.$BUILD_ID omranihadhemi/$JOB_NAME:latest'

                }
            }
        }
        stage ('Docker Hub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerhub_pwd', variable: 'docker_hub_cred')]) {
                        sh 'docker login -u omranihadhemi -p ${docker_hub_cred}'
                        sh 'docker image push omranihadhemi/$JOB_NAME:v1.$BUILD_ID '
                        sh 'docker image push omranihadhemi/$JOB_NAME:latest '
    
                 }

                }
            }

        }
        stage('Docker Compose Down') {
            steps {
                script {
                    // Arrête et supprime les conteneurs du service défini dans docker-compose.yml
                    sh 'docker compose down'
                }
            }
        }
        stage('Docker Compose Up') {
            steps{
                script{
                    sh 'docker compose up -d'
                }
            }
        }
        stage('Prometheus'){
            steps{
                script{
                    sh 'docker start prometheus'
                }
            }
        }
        stage('Grafana'){
            steps{
                script{
                    sh 'docker start grafana'
                }
            }
        }
        stage('Cleanup Old Docker Images') {
            steps {
                script {
                    // Supprime les images inutilisées
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

            