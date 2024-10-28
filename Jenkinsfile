pipeline {
    agent any

    stages{

        stage ('Git Chekout '){
            steps{
                git branch: 'hadhemiOmrani_5NIDS_G1', credentialsId: '7fa5bc14-ec8c-4e10-98e9-e20a76bf359d', url: 'https://github.com/yesminezaghdene03/5NIDS1_G1_TPFOYER.git'
            }
        }
        stage ('UNIT Testing '){
            steps{
                sh 'mvn test'
                
            }
        }
        stage ('Integration Testing '){
            steps{
                sh 'mvn verify -DskipUnitTests'
                
            }
        }
        stage ('Maven Building '){
            steps{
                sh 'mvn clean install'
                
            }
        }
        stage ('SonarQube Analysis '){
            steps{
                script {
                    withSonarQubeEnv(credentialsId: 'sonar-api-key') {
                 sh 'mvn clean package sonar:sonar'
                
    
            }}
                
                
            }
        }
        stage ('Quality Gate Status'){
            steps{
                script {
                    waitForQualityGate abortPipeline: false, credentialsId: 'sonar-api-key'

                }
                
            }
        }
    
    }
}