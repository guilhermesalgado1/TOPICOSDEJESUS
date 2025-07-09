pipeline {
    agent any  // executa no agente padrão (master, que no nosso caso é o próprio contêiner Jenkins)

    triggers {
        // Agendador para rodar todo dia às 00:00 (meia-noite)
        cron('0 0 * * *')
    }

    stages {
        stage('Checkout do Código') {
            steps {
                // Clonar do repositório Git local (ajuste a URL conforme seu path montado)
                git url: 'https://github.com/guilhermesalgado1/TOPICOSDEJESUS.git', branch: 'main'
                // Obs: Se tivesse credenciais (para repo remoto privado), configuraria usando Jenkins credentials.
            }
        }

        stage('Remover Containers e Imagens Antigos') {
            steps {
                // Remover contêineres se estiverem rodando (ignora erro se não existir, por isso '|| true')
                sh 'docker rm -f frontend-container || true'
                sh 'docker rm -f api1-container || true'
                sh 'docker rm -f api2-container || true'
                // Remover imagens antigas para liberar espaço (|| true para não falhar se não existir)
                sh 'docker rmi topicosdejesus/frontend:latest || true'
                sh 'docker rmi topicosdejesus/api1:latest || true'
                sh 'docker rmi topicosdejesus/api2:latest || true'
            }
        }

        stage('Build das Imagens Docker') {
            steps {
                // Construir imagem do Front-end (Next.js)
                sh 'docker build -t topicosdejesus/frontend:latest -f frontend/Dockerfile frontend/'
                sh 'docker build -t topicosdejesus/api1:latest -f api1/Dockerfile api1/'
                sh 'docker build -t topicosdejesus/api2:latest -f api2/Dockerfile api2/'
            }
        }

        stage('Subir Novos Containers') {
            steps {
                // Subir container do Front-end (expondo porta 3000 por exemplo)
                sh 'docker run -d --name frontend-container -p 3000:3000 topicosdejesus/frontend:latest'
                // Subir container da API 1 (expondo porta 8080)
                sh 'docker run -d --name api1-container -p 8080:8080 topicosdejesus/api1:latest'
                // Subir container da API 2 (expondo porta 4000)
                sh 'docker run -d --name api2-container -p 4000:4000 topicosdejesus/api2:latest'
            }
        }
    }

    post {
        // Enviar e-mail em caso de sucesso ou falha
        success {
            emailext(
                to: 'samuelmoro@alunos.utfpr.edu.br',
                subject: "[SUCESSO] Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Pipeline executado com sucesso em ${env.BUILD_TIMESTAMP}. Build #${env.BUILD_NUMBER} finalizado com sucesso."
            )
        }
        failure {
            emailext(
                to: 'samuelmoro@alunos.utfpr.edu.br',
                subject: "[FALHA] Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Pipeline falhou na execuçao em ${env.BUILD_TIMESTAMP}. Favor verificar os logs do Jenkins para detalhes."
            )
        }
    }
}