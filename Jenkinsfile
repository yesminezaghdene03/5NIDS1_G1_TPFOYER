pipeline {
    agent any

    stages {
        stage('GIT') {
            steps {
                git branch: 'AmineDridia_5NIDS1_G1',
                    url: 'https://github.com/yesminezaghdene03/5NIDS1_G1_TPFOYER'
            }
        }

        // Autres étapes de compilation, analyse, déploiement, etc.

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
        always {
            emailext(
                subject: "Build de ${env.JOB_NAME} #${env.BUILD_NUMBER} - ${currentBuild.result ?: 'SUCCESS'}",
                body: """<p>Bonjour,</p>
                         <p>Le build <b>#${env.BUILD_NUMBER}</b> du job <b>${env.JOB_NAME}</b> est terminé avec le statut <b>${currentBuild.result ?: 'SUCCESS'}</b>.</p>
                         <p>Cliquez <a href="${env.BUILD_URL}">ici</a> pour voir les détails du build.</p>""",
                to: 'aminedridia9@gmail.com'
            )
        }
    }
}
