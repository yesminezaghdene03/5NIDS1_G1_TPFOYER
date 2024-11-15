pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    stages {
        // Development Phase: Pre-commit Security Hooks
        stage('GIT') {
            steps {
                git branch: 'AmineDridia_5NIDS1_G1',
                    url: 'https://github.com/yesminezaghdene03/5NIDS1_G1_TPFOYER'
            }
        }

        stage('Pre-commit Security Hooks') {
            steps {
                script {
                    sh '''
                    if ! command -v pre-commit &> /dev/null
                    then
                        echo "Installing pre-commit in a virtual environment..."
                        python3 -m venv venv
                        . venv/bin/activate
                        pip install pre-commit
                    fi
                    git config --unset-all core.hooksPath
                    pre-commit install
                    pre-commit run --all-files
                    '''
                }
            }
        }

        // Commit Phase: JUnit/Mockito Security Unit Tests
        stage('Compile Stage') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('JUnit/Mockito Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('JaCoCo Report') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

        stage('JaCoCo coverage report') {
            steps {
                step([$class: 'JacocoPublisher',
                      execPattern: '**/target/jacoco.exec',
                      classPattern: '**/classes',
                      sourcePattern: '**/src',
                      exclusionPattern: '*/target/**/,**/*Test*,**/*_javassist/**'
                ])
            }
        }

        // Acceptance Phase: Security Scanning Tools
        stage('Scan : SonarQube') {
            steps {
                withSonarQubeEnv('sq1') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Security Scan: Nmap') {
            steps {
                script {
                    echo "Starting Nmap Security Scan..."
                    sh 'sudo nmap -sS -p 1-65535 -v localhost'
                }
            }
        }

        stage('Security Scan: OWASP Dependency-Check') {
            steps {
                script {
                    echo "Starting OWASP Dependency-Check..."
                    sh 'mvn org.owasp:dependency-check-maven:check'
                }
            }
        }

        // Production Phase: Deployment and Monitoring
        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy -DskipTests -DaltDeploymentRepository=deploymentRepo::default::http://192.168.33.10:8081/repository/maven-releases/'
            }
        }

        stage('Building Docker Image') {
            steps {
                sh 'docker build -t aminedridia/tp-foyer:5.0.0 .'
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                withCredentials([string(credentialsId: 'dockerhub-jenkins-token', variable: 'dockerhub_token')]) {
                    sh '''
                        docker login -u aminedridia -p aminedridia
                        docker push aminedridia/tp-foyer:5.0.0
                    '''
                }
            }
        }

        stage('Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }

        // Operations Phase: Container and Pipeline Monitoring
        stage('Start Monitoring Containers') {
            steps {
                echo "Starting monitoring container..."
                sh 'docker start be79135ec1cc'
            }
        }

        stage('Email Notification') {
            steps {
                mail bcc: '', 
                     body: '''
Final Report: The pipeline has completed successfully. No action is required.
''', 
                     cc: '', 
                     from: '', 
                     replyTo: '', 
                     subject: 'Pipeline Success - DevOps Project', 
                     to: 'aminedridia9@gmail.com'
            }
        }
    }

    post {
        success {
            script {
                emailext (
                    subject: "Build Success: ${currentBuild.fullDisplayName}",
                    body: "Le build a réussi ! Consultez les détails à ${env.BUILD_URL}",
                    recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'DevelopersRecipientProvider']],
                    to: 'aminedridia9@gmail.com'
                )
            }
        }
        failure {
            script {
                emailext (
                    subject: "Build Failure: ${currentBuild.fullDisplayName}",
                    body: "Le build a échoué ! Vérifiez les détails à ${env.BUILD_URL}",
                    recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'DevelopersRecipientProvider']],
                    to: 'aminedridia9@gmail.com'
                )
            }
        }
        always {
            script {
                emailext (
                    subject: "Build Notification: ${currentBuild.fullDisplayName}",
                    body: "Consultez les détails du build à ${env.BUILD_URL}",
                    recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'DevelopersRecipientProvider']],
                    to: 'aminedridia9@gmail.com'
                )
            }
        }
    }
}
