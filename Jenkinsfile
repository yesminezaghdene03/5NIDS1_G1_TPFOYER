pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-jenkins-token')
        SONARQUBE_ENV = 'sq1'
    }

    stages {
        // PHASE 1: Development Phase
        stage('GIT') {
            steps {
                git branch: 'AmineDridia_5NIDS1_G1',
                    url: 'https://github.com/yesminezaghdene03/5NIDS1_G1_TPFOYER'
            }
        }

        stage('Pre-Commit Security Hooks') {
            steps {
                script {
                    sh '''
                    if ! command -v pre-commit &> /dev/null
                    then
                        echo "Installing pre-commit..."
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

        stage('Static Code Analysis (SonarQube)') {
            steps {
                withSonarQubeEnv('sq1') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Unit Testing with JUnit/Mockito') {
            steps {
                sh 'mvn test'
            }
        }

        // PHASE 2: Commit Stage
        stage('JaCoCo Report') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

        stage('JaCoCo Coverage Report') {
            steps {
                step([$class: 'JacocoPublisher',
                      execPattern: '**/target/jacoco.exec',
                      classPattern: '**/classes',
                      sourcePattern: '**/src',
                      exclusionPattern: '**/target/**/,**/*Test*,**/*_javassist/**'
                ])
            }
        }

        stage('Security Scan with TruffleHog') {
            steps {
                script {
                    echo "Scanning for sensitive information with TruffleHog..."
                    sh '''
                    if ! command -v trufflehog &> /dev/null
                    then
                        echo "Installing TruffleHog..."
                        pip install trufflehog
                    fi
                    trufflehog filesystem . --json > trufflehog_report.json
                    '''
                }
            }
        }

        // PHASE 3: Production Phase
        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy -DskipTests -DaltDeploymentRepository=deploymentRepo::default::http://192.168.33.10:8081/repository/maven-releases/'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t aminedridia/tp-foyer:5.0.0 .'
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-jenkins-token', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        echo "${DOCKER_PASS}" | docker login -u "${DOCKER_USER}" --password-stdin
                        docker push aminedridia/tp-foyer:5.0.0
                    '''
                }
            }
        }

        stage('Run Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }

        // PHASE 4: Operations Phase
        stage('Start Monitoring Containers') {
            steps {
                script {
                    echo "Starting monitoring container..."
                    sh 'docker start be79135ec1cc' 
                }
            }
        }

        stage('Email Notification') {
            steps {
                mail bcc: '', 
                     body: '''
Pipeline Report: The pipeline has completed successfully. No further action is required.
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
    }
}
