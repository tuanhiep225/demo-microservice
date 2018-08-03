node {

    withMaven(maven:'M3') {

        stage('Checkout') {
            git url: 'https://github.com/tuanhiep225/demo-microservice.git', branch: 'master'
        }

        stage('Stop'){
            sh 'docker ps -f name=account -q | xargs --no-run-if-empty docker container stop'
            sh 'docker container ls -a -f name=account -q | xargs -r docker container rm'
            sh 'docker ps -f name=customer -q | xargs --no-run-if-empty docker container stop'
            sh 'docker container ls -a -f name=customer -q | xargs -r docker container rm'
            sh 'docker ps -f name=gateway -q | xargs --no-run-if-empty docker container stop'
            sh 'docker container ls -a -f name=gateway -q | xargs -r docker container rm'
            sh 'docker ps -f name=discovery -q | xargs --no-run-if-empty docker container stop'
            sh 'docker container ls -a -f name=discovery -q | xargs -r docker container rm'
        }
          
        stage("Build and deploy image") {
                withMaven(
                        maven: 'M3',
                        mavenLocalRepo: '.repository') {

                    // Run the maven build
                    sh "mvn clean package -DskipTests"
                }
        }
        stage("Start") {
                sh "docker-compose up -d --build"

        }

    }

}