pipeline {
    agent any

    stages {
        stage('Github clone') {
            steps {
                git branch: 'develop', credentialsId: 'e-market', url: 'https://github.com/sosow0212/2024-electronic-market.git'
            }
        }

        stage('Build') {
            steps {
                sh "./gradlew build"
                sh "./gradlew build"
            }
        }

        stage('Deploy') {
            steps {
                dir('build/libs') { // 프로젝트 빌드 후 jar 산출물 경로
                    sshagent(credentials: ['key-e-market']) {
                        sh 'scp -o StrictHostKeyChecking=no market-0.0.1-SNAPSHOT.jar ubuntu@server_ip:/home/ubuntu'
                        sh 'ssh -o StrictHostKeyChecking=no ubuntu@server_ip "sh deploy-market.sh" &'
                    }
                }
            }
        }

        stage('Slack Notification') {
            steps {
                script {
                    currentBuild.result = 'SUCCESS'
                    def slackMessage = 'Jenkins Deploy Notification: Build successful'
                    def slackColor = '#2C953C'

                    if (currentBuild.result == 'FAILURE') {
                        slackMessage = 'Jenkins Deploy Notification: Build failed'
                        slackColor = '#FF3232'
                    }

                    slackSend (
                            channel: 'jenkins-deploy',
                            color: slackColor,
                            message: slackMessage
                    )
                }
            }
        }
    }
}


