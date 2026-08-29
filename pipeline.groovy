pipeline {
    agent any

    stages {

        stage("Pull") {
            steps {
                git 'https://github.com/cloudmaster2025/sonar.git'
            }
        }

        stage("Build") {
            steps {
                sh '/opt/apache-maven-3.9.16/bin/mvn clean package'
            }
        }

        stage("Test") {
            steps {
                withSonarQubeEnv(
                    installationName: 'sonar',
                    credentialsId: 'sonar-token'
                ) {
                    sh '''
                        mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
                        -Dsonar.projectKey=student-app \
                        -Dsonar.projectName='student-app'
                    '''
                }
            }
        }

        stage("Quality-Gate") {
            steps {
                timeout(time: 10, unit: 'SECONDS') {
                waitForQualityGate abortPipeline: true, credentialsId: 'sonar-token'
                
                }
               
            }
        }

        stage("Deploy") {
            steps {
                echo 'Deploy success'
            }
        }
    }
}
