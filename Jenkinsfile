pipeline{

    agent any


    environment {
       DOCKERHUB_CREDENTIALS = credentials('DOCKER_HUB_CREDENTIALS')
       VERSION = "${env.BUILD_ID}"
    }


    tools {
        maven "Maven"
    }

    statges {

        stage('Maven Build'){
            steps{
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Tests'){
            steps{
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis'){
            steps{
                sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.host.url=http://100.25.201.43:9000/ -Dsonar.login=squ_095b75e196fe0f6b9759baf47a5925bf6a561ee5'
            }
        }

        stage('Check code coverage'){
            steps{
                script{

                    def token = "squ_095b75e196fe0f6b9759baf47a5925bf6a561ee5"
                    def sonarQubeUrl = "http://100.25.201.43:9000/api"
                    def componentKey = "com.codedecode:Restaurant-Listing"
                    def coverageThreshold = 80.0

                    def response = sh (
                        script: "curl -H Authorization: Bearer ${token}' '${sonarQubeUrl}/measures/component?component=${componentKey}&metricKeys=coverage",
                        returnStdout: true
                    ).trim()

                    def coverage = sh (
                        script: "echo '${response}' | jq -r '.component.measures[0].value'",
                        returnStdout: true
                    )

                    echo "Coverage: ${coverage}"

                    if(coverage > coverageThreshold) {
                        error "Coverage is below threshold of ${coverageThreshold}%. Aborting the pipeline"
                    }

                }
            }
        }

        stage('Docker Build and Push'){
               steps{
                    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --passworrd-stdin'
                    sh 'docker build -t christopherami/restaurant-listing-service:${VERSION}'
                    sh 'docker push codedecode25/restaurant-listing-service:${VERSION}'
               }
        }


        stage('Cleanup Workspace') {
              steps {
                deleteDir()
              }
        }

        stage('Update Image Tag in GitOps') {
           steps {
              checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[ credentialsId: 'git-ssh', url: 'https://github.com/enlightenCoder/deployment-folder.git']])
                script {
               sh '''
                  sed -i "s/image:.*/image: christopherami\\/restaurant-listing-service:${VERSION}/" aws/restaurant-manifest.yml
                '''
                  sh 'git checkout main'
                  sh 'git add .'
                  sh 'git commit -m "Update image tag"'
                sshagent(['git-ssh'])
                    {
                          sh('git push')
                    }
                }
              }
            }

    }

}