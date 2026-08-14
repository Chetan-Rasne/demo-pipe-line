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
                sh ''' /opt/maven/bin/mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  		-Dsonar.projectKey=student-b42 \
  		-Dsonar.projectName='student-b42' \
  		-Dsonar.host.url=http://15.206.116.70:9000 \
  		-Dsonar.token=sqp_c9482d0d6b12aeca88eb0dac1fa230dcf3230590'''

            }
        }
        stage ("Deploy"){
            steps {
                echo 'Deploy success'
            }
        }
        
        
        
        
    }    
}
