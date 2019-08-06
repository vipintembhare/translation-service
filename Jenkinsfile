def label = "worker-${UUID.randomUUID().toString()}"

podTemplate(label: label, containers: [
  containerTemplate(name: 'maven', image: 'maven:3.3.9-jdk-8-alpine', ttyEnabled: true, command: 'cat'),
  containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true),
  containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl:v1.8.8', command: 'cat', ttyEnabled: true),
],
volumes: [
  hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
]) {
  node(label) {
        def PROJECT_ID='conference-console'
        def IMAGE_NAME='translation-service'
        def ENV='dev'    
        def IMAGE_TAG="$PROJECT_ID/$IMAGE_NAME:latest"
    
     stage ('Pull') {
        checkout scm
        }
        stage ('Build Stage') {

          container('maven') {
                    sh 'mvn clean install -DskipTests'
            }
        }
    stage('Docker stage') {
      container('docker') {
              def image = docker.build("${IMAGE_TAG}")
                    println "Newly generated image, " + image.id
                    
                    docker.withRegistry('https://gcr.io', 'gcr:gke-jenkins-key') {
                           image.push('latest')
                    }

      }
    }
   stage('Deploy Stage') {
      container('kubectl') {
          withKubeConfig([credentialsId: 'devcluster', serverUrl: 'https://35.222.86.49']) {
       sh "kubectl apply -f deployment.yaml"
      }
      }
    }
  }
}
