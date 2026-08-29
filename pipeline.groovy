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
               deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'admin-tomcat', path: '', url: 'http://13.201.226.252:8080')], contextPath: 'student.war', war: 'target/*.war'
            }
        }
    }
}
