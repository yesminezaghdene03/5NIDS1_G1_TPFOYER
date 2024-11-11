pipeline {
    agent any

    stages {
        stage('GIT') {
            steps {
                git branch: 'AmineDridia_5NIDS1_G1',
                    url: 'https://github.com/yesminezaghdene03/5NIDS1_G1_TPFOYER'
            }
        }

        /* 
        stage('Build') {
            steps {
                sh 'mvn clean compile' // Étape de compilation
            }
        }
        */

        /* 
        stage('Scan') {
            steps {
                withSonarQubeEnv('sq1') {
                    sh 'mvn sonar:sonar' // Analyse SonarQube avec les classes compilées
                }
            }
        }
        */

        /* 
        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy -DskipTests -DaltDeploymentRepository=deploymentRepo::default::http://192.168.33.10:8081/repository/maven-releases/'
            }
        }
        */

        stage('Build Docker Image') {
            steps {
                sh 'sudo docker build -t aminedridia/tp-foyer:5.0.0 .'
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                sh '''
                    sudo docker login -u aminedridia -p adminamine 
                    sudo docker push aminedridia/tp-foyer:5.0.0
                '''
            }
        }

        stage('Run Docker Compose') {
            steps {
                script {
                    sh '''
                        sudo docker-compose down 
                        sudo docker-compose up -d
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Build and deployment successful!'
            mail to: 'aminedridia9@gmail.com',
                 subject: "Build Success - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "The build was successful! Check the details at ${env.BUILD_URL}"
        }
        failure {
            mail to: 'aminedridia9@gmail.com',
                 subject: "Build Failure - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "The build failed. Please check the details at ${env.BUILD_URL}"
        }
    }
}
