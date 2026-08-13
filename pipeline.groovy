pipeline {
    agent any
    stages {
        stage ("Pull"){
            steps {
                git ' https://github.com/cloudmaster2025/sonar.git'
            }
        }
        stage ("Build"){
            steps {
                sh '/opt/apache-maven-3.9.16/bin/mvn clean package'
            }
        }
        stage ("Test"){
            steps {
                echo 'Test success'
            }
        }
        stage ("Deploy"){
            steps {
                echo 'Deploy success'
            }
        }
        
        
        
        
    }    
}
