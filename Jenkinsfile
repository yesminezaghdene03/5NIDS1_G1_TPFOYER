pipeline {
    agent any

    stages {

        stage ('Git Checkout') {
            steps {
                git branch: 'hadhemiOmrani_5NIDS_G1', credentialsId: '7fa5bc14-ec8c-4e10-98e9-e20a76bf359d', url: 'https://github.com/yesminezaghdene03/5NIDS1_G1_TPFOYER.git'
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
        
        stage ('Maven Building') {
            steps {
                sh 'mvn clean install'
            }
        }
        
        stage ('JaCoCo Report') {
            steps {
                // Génère le rapport de couverture de code JaCoCo
                sh 'mvn jacoco:report'
            }
        }
        
        stage ('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv(credentialsId: 'sonar-api-key') {
                        // Exécute l'analyse SonarQube en incluant le rapport JaCoCo
                        sh 'mvn clean package sonar:sonar -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml'
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
                    repository: 'Tpfoyer-Release', 
                    version: '5.0.0'
                }
            }
        }
    }
}