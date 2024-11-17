# Usar a imagem do JDK 17 como base
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo JAR gerado pelo Maven/Gradle para o diretório de trabalho
COPY target/energyx-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta em que a aplicação irá rodar
EXPOSE 8081

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]