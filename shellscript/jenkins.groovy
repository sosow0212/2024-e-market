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

        stage('Docker process') {
            steps {
                sh 'docker build --platform linux/arm64/v8 -t docker_hub_nickname/docker_hub_repository:latest -f Dockerfile .'
                sh 'docker login -u docker_hub_nickname -p docker_hub_password'
                sh 'docker push docker_hub_nickname/docker_hub_repository:latest'
            }
        }

        stage('Deploy') {
            steps {
                dir('build/libs') {
                    sshagent(credentials: ['key-e-market']) {
                        sh 'ssh -o StrictHostKeyChecking=no ubuntu@server_ip "cd /home/ubuntu/emarket && sh deploy.sh"'
                    }
                }
            }
        }
    }
}
