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
                sh 'mvn verify -DskipUnitTests'
            }
        }
        stage ('Maven Build') {
            steps {
                sh 'mvn clean install'
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
        stage ('Docker Compose Up') {
            steps {
                script {
                    // Démarrer les services avec Docker Compose
                    sh 'docker-compose up -d'
                }
            }
        }

    }
}
